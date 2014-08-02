Eureka
======
The Eureka mod introduces a new knowledge system to modded minecraft: "Learning as you go". With this system the intention is that there is no borring mini-game, no need to craft alot of additional items and 'sacrifice' those items to be able to make new ones. With this the idea is that the player progresses through the tech try by using the machine or making items that involve that type of tech while playing. Like as if the player is crafting/using stuff and then suddenly thinks: 'There might be a better way to do this'. But until they realise that knowledge the are unable to use the higher tiers. The player will still be able to craft those items/blocks but the eureka mod prevent using the item and block the interaction with the block. If a player tries it the item/block will fall appart

An example of that is my own mod 'Buildcraft Additions' (i wrote this system for that mod but others can us it to). To unlock the kinetic tools you need to make 10 tools. As 10 tools isnt' that much (since the first wooden and stone tools get replaced easy and pickaxes break during mining) the player will unlock it while playing as he goes without having to do additional crafting to unlock it.

How to implement the mod
=====

This is how to implement the eureka stuff in your own mod

Registering your keys
====
For the eureka system to succesfully track the progress of a player you need to register 'keys'. These keys are also used for the localizations of the eureka moments and the text in the book. In the registration you also have to tell the eureka system how many times progress has to be made and in what increments. You register like this:
```
EurekaRegistry.registerKey(<key>, <progress needed to unlock the next tier>, <progress increments>, <itemstack to display on the tab in the book>);
```
The Eureka knowledge
====
As the Eureka system isn't all knowing you have to tell it when a player makes progress like so:
```
EurekaKnowledge.makeProgress(<player that makes progress>)
```

If you want to know if a player has finished the tier and got the knowledge for the next one
```
EurekaKnowledge.isFinished(<player to check>, <key to check>);
```

WARNING: The eureka stuff is all saved and handled on the server side. Every time the player opens the book, the knowledge is also coppied to the client side so it all shows up in the gui. So the data on the client side will be outdated most of the time!

Blocks
====
For blocks you implement the [IEurekaBlock](https://github.com/AEnterprise/Eureka/blob/master/src/main/java/eureka/interfaces/IEurekaBlock.java) interface. This has a few functions:

isAllowed(player): Every time the eureka system checks if the player has the required knowledge or not this function is called. Here you can just return if it's allowed in the eurekaKnowledge but you can also perform additional checks of your own if you want.

getComponents(): Here you return an array of itemstacks (can be any size, even empty). If the block is not allowed to be used by the player, the block will be replaced with air and these stacks will fall on the ground

getMessage(): If the block was not allowed this is the message printed out to the chat of that player

example: [Buildcraft Additions Kinetic Duster](https://github.com/AEnterprise/Buildcraft-Additions/blob/master/src/main/java/buildcraftAdditions/blocks/BlockKineticDuster.java) (also has additional check when the block is placed down)

Items
====
The same as for a block, only this time you implement [IEurekaItem](https://github.com/AEnterprise/Eureka/blob/master/src/main/java/eureka/interfaces/IEurekaItem.java) instead of IEurekaBlock. The item will be checked every time the player clicks with the item in his head (both left and right will be checked)

Manual checks
====
Sometimes the default block checks might not be enough. If you have a fully automatable block for example then players could bypass the regular checks. They could place all the pipes, hoppers and powerlines prior to placing the block, preventing an eureka check. If you want it to check additionally you can just call the EurekaBlockEvent in the eurekaKnowledge class. This will perform the check for you and do the destorying, dropping and chatting.

Example: [Buildcraft Additions Kinetic Multi-Tool](https://github.com/AEnterprise/Buildcraft-Additions/blob/master/src/main/java/buildcraftAdditions/items/Tools/ItemKineticTool.java)


