package miningtweaks;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

/**
 * Baritone madencilik ayarlarini dunyaya girince bir kez uygular.
 * Sonrasinda oyun icinden #set ile istedigin ayari degistirebilirsin,
 * mod tekrar uzerine yazmaz (yeni dunyaya/sunucuya girince yeniden uygular).
 */
public class MiningTweaks implements ClientModInitializer {
    // --- Ayarlar: istedigin gibi degistir ---
    private static final boolean ONLY_EXPOSED_ORES = true;   // odun kazarken false yap
    private static final int EXPOSED_DISTANCE = 1;
    private static final double BREAK_PENALTY = 20.0;        // yuksek = temizlemekten kacinir ama atlamaz
    private static final int GOAL_UPDATE_INTERVAL = 5;       // dusuk = hedef listesi sik yenilenir
    private static final boolean SMOOTH_LOOK = true;
    private static final int SMOOTH_LOOK_TICKS = 10;

    private boolean applied;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
    }

    private void tick(Minecraft mc) {
        if (mc.player == null) { applied = false; return; }
        if (applied) return;
        if (BaritoneAPI.getProvider().getPrimaryBaritone() == null) return;
        applied = true;

        Settings s = BaritoneAPI.getSettings();
        s.legitMine.value = false;
        s.allowBreak.value = true;               // false yapma: etrafi kirmayinca hedefleri atliyor
        s.allowPlace.value = false;
        s.blockBreakAdditionalPenalty.value = BREAK_PENALTY;
        s.allowOnlyExposedOres.value = ONLY_EXPOSED_ORES;
        s.allowOnlyExposedOresDistance.value = EXPOSED_DISTANCE;
        s.mineScanDroppedItems.value = true;
        s.mineGoalUpdateInterval.value = GOAL_UPDATE_INTERVAL;
        s.smoothLook.value = SMOOTH_LOOK;
        s.smoothLookTicks.value = SMOOTH_LOOK_TICKS;
    }
}
