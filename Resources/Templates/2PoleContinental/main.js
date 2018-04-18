//Fundamental stuff
var xSize = window.getWidth();
var ySize = window.getHeight();
var graphics = window.getGraphics();
//declare classes
var int_Array = Java.type("int[]");
var double_Array = Java.type("double[]");

var FastNoise = Java.type("fastnoise.FastNoise");
var NoiseType = Java.type("fastnoise.FastNoise$NoiseType");
var FractalType = Java.type("fastnoise.FastNoise$FractalType");
var CellularDistanceFunction = Java.type("fastnoise.FastNoise$CellularDistanceFunction");

var WorldUtils = Java.type("worldUtils.WorldUtils");
var ShortMap = Java.type("worldUtils.ShortMap");
var ShortMap_Array = Java.type("worldUtils.ShortMap[]");

//configure my various types of noise 
var randomNoise_FastNoise = new FastNoise();
randomNoise_FastNoise.SetNoiseType(NoiseType.SimplexFractal);

var continentNoise_FastNoise = new FastNoise();
continentNoise_FastNoise.SetNoiseType(NoiseType.Simplex);


var mountainNoise_FastNoise = new FastNoise();
mountainNoise_FastNoise.SetNoiseType(NoiseType.Simplex);
mountainNoise_FastNoise.SetFrequency(5.0);

var randomNoise_ShortMap = WorldUtils.noise(randomNoise_FastNoise, xSize,ySize);
var continentNoise_ShortMap = WorldUtils.noise(continentNoise_FastNoise, xSize, ySize);
var mountainNoise_ShortMap = WorldUtils.noise(mountainNoise_FastNoise, xSize, ySize);



mountainNoise_ShortMap = WorldUtils.abs(mountainNoise_ShortMap, 0,0, xSize,ySize)
mountainNoise_ShortMap = WorldUtils.negate(mountainNoise_ShortMap, 0,0, xSize,ySize)
mountainNoise_ShortMap = WorldUtils.add(mountainNoise_ShortMap, 1, 0,0, xSize,ySize)


var noise_ShortMap_Array = new ShortMap_Array(3);
noise_ShortMap_Array[0] = randomNoise_ShortMap;
noise_ShortMap_Array[1] = continentNoise_ShortMap;
noise_ShortMap_Array[2] = mountainNoise_ShortMap;

var noise_double_Array = new double_Array(3);
noise_double_Array[0] = 0.2;
noise_double_Array[1] = 0.5;
noise_double_Array[2] = 0.3;


sum = WorldUtils.weightedAverage(noise_ShortMap_Array, noise_double_Array, 0, 0, xSize, ySize);


graphics.drawImage(mountainNoise_ShortMap.getImage(), 0,0,null);
