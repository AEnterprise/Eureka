Eureka
======
The Eureka mod introduces a new knowledge system to modded minecraft: "Learning as you go". With this system the intention is that there is no borring mini-game, no need to craft alot of additional items and 'sacrifice' those items to be able to make new ones. With this the idea is that the player progresses through the tech try by using the machine or making items that involve that type of tech while playing. Like as if the player is crafting/using stuff and then suddenly thinks: 'There might be a better way to do this'. But until they realise that knowledge the are unable to use the higher tiers. The player will still be able to craft those items/blocks but the eureka mod prevent using the item and block the interaction with the block. If a player tries it the item/block will fall appart

An example of that is my own mod 'Buildcraft Additions' (i wrote this system for that mod but others can us it to). To unlock the kinetic tools you need to make 10 tools. As 10 tools isnt' that much (since the first wooden and stone tools get replaced easy and pickaxes break during mining) the player will unlock it while playing as he goes without having to do additional crafting to unlock it.

How to implement the mod
=====

This is how to implement the eureka stuff in your own mod

Registering you categories
====
To show your keys you need to fist make a category for where your keys will be, this is done by registering a new category in the registery
````
EurekaRegistry.registerCategory(<category name>, <displaystack>);
````
The displaystack is the itemstack show in the engineering diary to represent the category

Registering your keys
====
For the eureka system to succesfully track the progress of a player you need to register 'keys'. These keys are also used for the localizations of the eureka moments and the text in the book. In the registration you also have to tell the eureka system how many times progress has to be made and in what increments. You register like this:
```
EurekaRegistry.registerKey(<key>, <EurekaInformation>);
```

The second argument there is a class that should extend EurekaInformation that contains all info about the specified entry (There is a class EurekaInfo you can use for a basic implementation).

then you need to register the drops for the key like so:
````
EurekaRegistry.registerDrops(<key>, itemstack1, itemstack2, ...);
````
you can specify as much itemstacks to drop as you want as long as it's at least 1.

and finaly you bind the item/block to the key so eureka knows to track it
````
EurekaRegistry.bindToKey(<item/block>, <key>);
````

The Eureka knowledge
====
As the Eureka system isn't all knowing you have to tell it when a player makes progress like so:
```
EurekaKnowledge.makeProgress(player, key, amount)
```
note that the amount can also be negative

If you want to know if a player has finished the tier and got the knowledge for the next one
```
EurekaKnowledge.isFinished(<player to check>, <key to check>);
```

WARNING: The eureka stuff is all saved and handled on the server side. Every time the player opens the book, the knowledge is also coppied to the client side so it all shows up in the gui. So the data on the client side will be outdated most of the time!


Using the progress options provided by eureka
====
To have eureka take care of making progress for you you just add the key to the right progresslist. In the EurekaRegistry there is are functions to add keys to the right hashmap for that, the names should speak for themselfs but if you are not sure about what the right one to use is take a look at the eventhandeler, in there you can see what each list is used for and when it's trigered

Using your own methods for making progress
====
Do whatever you want to make progress and conditions to make progress, to make progress you call the function as specified above: 
```
EurekaKnowledge.makeProgress(player, key, amount)
```

as long as your block/item is bound to the key eureka will make sure the block can't be interacted with, placed or broken until the player has the required knowledge
