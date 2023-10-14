package cc.mewcraft.spatula.network;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class OriginUtils {
    /**
     * Gets the server-origin-id of specific user.
     */
    public static @Nullable String getOriginId(@NonNull User user) {
        return user
            .getCachedData()
            .getMetaData()
            .getMetaValue(NetworkConstants.SERVER_ORIGIN_ID_KEY);
    }

    /**
     * Gets the server-origin-name of specific user.
     */
    public static @Nullable String getOriginName(@NonNull User user) {
        return user
            .getCachedData()
            .getMetaData()
            .getMetaValue(NetworkConstants.SERVER_ORIGIN_NAME_KEY);
    }

    /**
     * Checks if specific user has a server origin.
     */
    public static boolean hasOrigin(@NonNull User user) {
        return getOriginId(user) != null;
    }

    /**
     * Checks if specific user is at his server origin.
     */
    public static boolean atOrigin(@NonNull User user) {
        @Nullable String origin = getOriginId(user);
        @Nullable String server = ServerInfo.SERVER_ID.get();
        return origin != null && origin.equals(server);
    }

    /**
     * Sets server origin for specific user, overwriting any existing ones.
     */
    public static boolean setOrigin(@NonNull User user) {
        LuckPerms luckPerms = LuckPermsProvider.get();

        // Remove existing ones
        user.data().clear(node ->
            node.getType() == NodeType.META && (
                node.getKey().equals(NetworkConstants.SERVER_ORIGIN_ID_KEY) ||
                node.getKey().equals(NetworkConstants.SERVER_ORIGIN_NAME_KEY)
            )
        );

        // Add nodes, and save results
        List<DataMutateResult> results = List.of(
            user.data().add(OriginMeta.SERVER_ORIGIN_ID.get()),
            user.data().add(OriginMeta.SERVER_ORIGIN_NAME_KEY.get())
        );

        // Save changes
        luckPerms.getUserManager().saveUser(user);

        // Return true if all nodes are added successfully
        return results.stream().allMatch(DataMutateResult::wasSuccessful);
    }

    /**
     * Removes server origin from specific user.
     */
    public static void removeOrigin(@NonNull User user) {
        LuckPerms luckPerms = LuckPermsProvider.get();

        // Remove nodes
        user.data().clear(node ->
            node.getType() == NodeType.META && (
                node.getKey().equals(NetworkConstants.SERVER_ORIGIN_ID_KEY) ||
                node.getKey().equals(NetworkConstants.SERVER_ORIGIN_NAME_KEY)
            )
        );

        // Save changes
        luckPerms.getUserManager().saveUser(user);
    }

    //<editor-fold desc="Expanded Methods">
    public static @Nullable String getOriginId(@NonNull UUID uuid) {
        return getUser(uuid).map(OriginUtils::getOriginId).orElse(null);
    }

    public static @Nullable String getOriginName(@NonNull UUID uuid) {
        return getUser(uuid).map(OriginUtils::getOriginName).orElse(null);
    }

    public static boolean hasOrigin(@NonNull UUID uuid) {
        return getUser(uuid).map(OriginUtils::hasOrigin).orElse(false);
    }

    public static boolean atOrigin(@NonNull UUID uuid) {
        return getUser(uuid).map(OriginUtils::atOrigin).orElse(false);
    }

    public static boolean setOrigin(@NonNull UUID uuid) {
        return getUser(uuid).map(OriginUtils::setOrigin).orElse(false);
    }

    public static void removeOrigin(@NonNull UUID uuid) {
        getUser(uuid).ifPresent(OriginUtils::removeOrigin);
    }

    private static @NonNull Optional<User> getUser(@NonNull UUID uuid) {
        return Optional.ofNullable(LuckPermsProvider.get().getUserManager().getUser(uuid));
    }
    //</editor-fold>

    private OriginUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
}
