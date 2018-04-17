//Fundamental stuff
var x_size = window.getWidth();
var y_size = window.getHeight();
var g2d = window.getGraphics();
//declare classes
var FastNoise = Java.type("fastnoise.FastNoise");
var NoiseType = Java.type("fastnoise.FastNoise$NoiseType");
var FractalType = Java.type("fastnoise.FastNoise$FractalType");
var CellularDistanceFunction = Java.type("fastnoise.FastNoise$CellularDistanceFunction");

var 

var fractal = new FastNoise();
fractal.SetNoiseType(NoiseType.Perlin);

