![Crossbow Enchants Banner](https://cdn.modrinth.com/data/cached_images/f197c588bdc5e765e649b201033cfe481c98ada5.png)

# Crossbow Enchants

Crossbow Enchants is a customizable server or client sided mod, that enhances the crossbow experience by enabling bow exclusive enchantments on the crossbow. It currently supports Fabric and Quilt.

In addition to that, the mod also supports making Infinity and Mending compatible on crossbows and bows, as well as making Piercing and Multishot compatible on crossbows.


Although this mod was initially made to work on the server side exclusively, it works perfectly fine on singleplayer too.
If you plan on running this mod on the server side, the players don't need to have it installed on the client side.

## Commands
Using the following commands, you can customize the functionality of the mod, note that players must have operator status to use these commands on a server:
- `/crossbowenchants <disable/enable>` - A toggle command for the global functionality of the mod.
- `/crossbowenchants enchantments <disable/enable> <enchantment>` - Disable or enable a specific enchantment.
- `/crossbowenchants features <disable/enable> <feature>` - Disable or enable a specific feature, features include Infinity-And-Mending, and Piercing-And-Multishot.
- `/crossbowenchants info` - This command outputs the current settings of the mod to the player.
- `/crossbowenchants reset` - This command resets the settings to default, by default, all enchantments and features are enabled.

## Configuration
You can also customize the mod directly from its config file, located at `.\config\crossbowenchants.toml`.

Here are the contents of the config file by default:
```toml
#A toggle for the global functionality of Crossbow Enchants.
Enabled = true
#A toggle for the functionality of the Flame enchantment on the crossbow.
FlameEnabled = true
#A toggle for the functionality of the Infinity enchantment on the crossbow.
InfinityEnabled = true
#A toggle for the functionality of the Power enchantment on the crossbow.
PowerEnabled = true
#A toggle for the functionality of the Punch enchantment on the crossbow.
PunchEnabled = true
#A toggle for the compatibility of Infinity and Mending on crossbows and bows.
InfinityAndMendingEnabled = true
#A toggle for the compatibility of Piercing and Multishot on crossbows.
PiercingAndMultishotEnabled = true
```
Note that the config will not reset if any property is missing or improperly formatted, instead, the mod will use the default value for that property, which is 'true' in all cases.

## Dependencies
Fabric API: Crossbow Enchants requires the Fabric API to function properly. Ensure you have it installed alongside the mod before starting your client or server.

## Additional Info
### Enchantment Behavior
Note that disabling any of the enchantments will result in two specific outcomes:

- The enchantments will no longer be appliable to crossbows through the use of an anvil.

- The enchantments will stop functioning on pre-existing crossbows that are enchanted with them.

### Feature Behavior
Note that disabling a feature will result in only one outcome:

- The enchantments that are affected by the feature will no longer be compatible with each other through the use of an anvil, but will work perfectly normal on pre-existing items that are enchanted with them.

### Infinity and Mending
Enabling the Infinity-And-Mending feature will make the Infinity and Mending enchantments universally compatible, meaning they will also work on bows.

In order to avoid making the crossbow too powerful in comparison to the bow, I decided to leave it as it is for now.
Although I might make a setting for this specific case in the future.

## Issues
This is the first mod I developed for Fabric and Quilt, and while there are no currently known issues, I doubt it will stay that way in the future.

If you run into any problems, don't hesitate to [submit an issue](https://github.com/TimVincii/Crossbow-Enchants/issues).

