//Fundamental stuff
var x_size = window.getWidth();
var y_size = window.getHeight();
var g2d = window.getGraphics();
//declare classes
var FastNoise = Java.type("fastnoise.FastNoise");
var NoiseType = Java.type("fastnoise.FastNoise$NoiseType");
var FractalType = Java.type("fastnoise.FastNoise$FractalType");
var CellularDistanceFunction = Java.type("fastnoise.FastNoise$CellularDistanceFunction");

var WorldUtils = Java.type("worldUtils.WorldUtils");
var ShortMap = Java.type("worldUtils.ShortMap");


var fractal = new FastNoise();
fractal.SetNoiseType(NoiseType.Perlin);

var thing = WorldUtils.noise(fractal, 300,300);

g2d.drawImage(thing.getImage(), 0,0,null);
