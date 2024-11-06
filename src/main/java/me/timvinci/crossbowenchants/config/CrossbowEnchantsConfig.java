package me.timvinci.crossbowenchants.config;

public class CrossbowEnchantsConfig {
    // Creating and setting a default value to the config options.
    private boolean enabled = true;
    private boolean flameEnabled = true;
    private boolean infinityEnabled = true;
    private boolean lootingEnabled = true;
    private boolean powerEnabled = true;
    private boolean punchEnabled = true;
    private boolean infinityAndMendingEnabled = true;
    private boolean piercingAndMultishotEnabled = true;

    // Creating setters and getters.
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFlameEnabled() {
        return flameEnabled;
    }

    public void setFlameEnabled(boolean flameEnabled) {
        this.flameEnabled = flameEnabled;
    }

    public boolean isInfinityEnabled() {
        return infinityEnabled;
    }

    public void setInfinityEnabled(boolean infinityEnabled) {
        this.infinityEnabled = infinityEnabled;
    }

    public boolean isLootingEnabled() { return lootingEnabled; }

    public void setLootingEnabled(boolean lootingEnabled) { this.lootingEnabled = lootingEnabled; }

    public boolean isPowerEnabled() {
        return powerEnabled;
    }

    public void setPowerEnabled(boolean powerEnabled) {
        this.powerEnabled = powerEnabled;
    }

    public boolean isPunchEnabled() {
        return punchEnabled;
    }

    public void setPunchEnabled(boolean punchEnabled) {
        this.punchEnabled = punchEnabled;
    }

    public boolean isInfinityAndMendingEnabled() {
        return infinityAndMendingEnabled;
    }

    public void setInfinityAndMendingEnabled(boolean infinityAndMendingEnabled) {
        this.infinityAndMendingEnabled = infinityAndMendingEnabled;
    }

    public boolean isPiercingAndMultishotEnabled() {
        return piercingAndMultishotEnabled;
    }

    public void setPiercingAndMultishotEnabled(boolean piercingAndMultishotEnabled) {
        this.piercingAndMultishotEnabled = piercingAndMultishotEnabled;
    }
}