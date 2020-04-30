# Tailwind Formatter

Tailwind formatter is a port of [Headwind](https://github.com/heybourn/headwind) for use with Intellij/Phpstorm/Webstorm. It enforces consistent ordering of classes by parsing your code and reprinting class tags to follow a given order.

## Download

[Download Here](https://plugins.jetbrains.com/plugin/13376-tailwind-formatter/)

## Usage
![Demo Gif](https://github.com/MyOutDeskLLC/Tailwind-Formatter/blob/master/demo.gif)
![Demo With @apply Gif](https://github.com/MyOutDeskLLC/Tailwind-Formatter/blob/master/Sort_Apply_Classes.gif)


Code -> Run Tailwind Formatter

Hotkey -> Shift + Ctrl + H

## Class Order
The default class order is available under src/TailwindUtility. Custom classes will automatically be pushed to the back
of the class list. Duplicated classes will also be removed.


## Customizing Sort Order
You can go to preferences and find TailwindFormatter. Here you will see an empty text area. If it is left empty we will assume the default order with tailwind-formatter. If you would like to change the order write one class per line. We sort from top to bottom.

Example: 

```
bg-red-500
p-4
h-15
mx-2
```

Note only the above 4 classes will  be sorted. If you would lke to import all the classes please click the button at the bottom of this dialog.


## License
MIT

