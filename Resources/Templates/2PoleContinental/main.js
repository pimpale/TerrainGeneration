//Fundamental stuff
var xSize = 1024;
var ySize = 1024;
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
var randomNoise0_FastNoise = new FastNoise(0);
randomNoise0_FastNoise.SetNoiseType(NoiseType.SimplexFractal);
randomNoise0_FastNoise.SetFrequency(0.01);
var randomNoise0_ShortMap = WorldUtils.noise(randomNoise0_FastNoise, xSize,ySize);

var randomNoise1_FastNoise = new FastNoise(1);
randomNoise1_FastNoise.SetNoiseType(NoiseType.SimplexFractal);
randomNoise1_FastNoise.SetFrequency(0.01);
var randomNoise1_ShortMap = WorldUtils.noise(randomNoise1_FastNoise, xSize,ySize);

var randomNoise2_FastNoise = new FastNoise(2);
randomNoise2_FastNoise.SetNoiseType(NoiseType.SimplexFractal);
randomNoise2_FastNoise.SetFrequency(0.01);
var randomNoise2_ShortMap = WorldUtils.noise(randomNoise2_FastNoise, xSize,ySize);

var randomNoise3_FastNoise = new FastNoise(3);
randomNoise3_FastNoise.SetNoiseType(NoiseType.SimplexFractal);
randomNoise3_FastNoise.SetFrequency(0.01);
var randomNoise3_ShortMap = WorldUtils.noise(randomNoise3_FastNoise, xSize,ySize);

var randomNoise4_FastNoise = new FastNoise(4);
randomNoise4_FastNoise.SetNoiseType(NoiseType.SimplexFractal);
randomNoise4_FastNoise.SetFrequency(0.01);
var randomNoise4_ShortMap = WorldUtils.noise(randomNoise4_FastNoise, xSize,ySize);

var randomNoise5_FastNoise = new FastNoise(5);
randomNoise5_FastNoise.SetNoiseType(NoiseType.SimplexFractal);
randomNoise5_FastNoise.SetFrequency(0.01);
var randomNoise5_ShortMap = WorldUtils.noise(randomNoise5_FastNoise, xSize,ySize);

var randomNoise6_FastNoise = new FastNoise(6);
randomNoise6_FastNoise.SetNoiseType(NoiseType.SimplexFractal);
randomNoise6_FastNoise.SetFrequency(0.01);
var randomNoise6_ShortMap = WorldUtils.noise(randomNoise6_FastNoise, xSize,ySize);

var mountainNoise_FastNoise = new FastNoise(7);
mountainNoise_FastNoise.SetNoiseType(NoiseType.SimplexFractal);
mountainNoise_FastNoise.SetFrequency(0.002);


var mountainNoise_ShortMap = WorldUtils.noise(mountainNoise_FastNoise, xSize, ySize);
mountainNoise_ShortMap = WorldUtils.abs(mountainNoise_ShortMap, 0,0, xSize,ySize)
mountainNoise_ShortMap = WorldUtils.negate(mountainNoise_ShortMap, 0,0, xSize,ySize)


var noise_ShortMap_Array = new ShortMap_Array(8);
noise_ShortMap_Array[0] = randomNoise0_ShortMap;
noise_ShortMap_Array[1] = randomNoise1_ShortMap;
noise_ShortMap_Array[2] = randomNoise2_ShortMap;
noise_ShortMap_Array[3] = randomNoise3_ShortMap;
noise_ShortMap_Array[4] = randomNoise4_ShortMap;
noise_ShortMap_Array[5] = randomNoise5_ShortMap;
noise_ShortMap_Array[6] = randomNoise6_ShortMap;
noise_ShortMap_Array[7] = mountainNoise_ShortMap;

var noiseWeight_double_Array = new double_Array(8);
noiseWeight_double_Array[0] = 0.1;
noiseWeight_double_Array[1] = 0.1;
noiseWeight_double_Array[2] = 0.1;
noiseWeight_double_Array[3] = 0.1;
noiseWeight_double_Array[4] = 0.1;
noiseWeight_double_Array[5] = 0.1;
noiseWeight_double_Array[6] = 0.1;
noiseWeight_double_Array[7] = 0.3;


sum = WorldUtils.weightedAverage(noise_ShortMap_Array, noiseWeight_double_Array, 8, 0, 0, xSize, ySize);
sum = WorldUtils.threshold(sum,0,0,0,xSize,ySize);

//sum = WorldUtils.fromDoubleStream(WorldUtils.toLossyDoubleStream(sum), xSize,ySize);


graphics.drawImage(sum.getImage(), 0,0,null);