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
var CellularReturnType = Java.type("fastnoise.FastNoise$CellularReturnType");

var WorldUtils = Java.type("worldUtils.WorldUtils");
var ShortMap = Java.type("worldUtils.ShortMap");
var ShortMap_Array = Java.type("worldUtils.ShortMap[]");

//configure my various types of noise 
var randomNoise0_FastNoise = new FastNoise(0);
randomNoise0_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise0_FastNoise.SetFrequency(0.000625);
var randomNoise0_ShortMap = WorldUtils.noise(randomNoise0_FastNoise, xSize,ySize);

var randomNoise1_FastNoise = new FastNoise(1);
randomNoise1_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise1_FastNoise.SetFrequency(0.00125);
var randomNoise1_ShortMap = WorldUtils.noise(randomNoise1_FastNoise, xSize,ySize);

var randomNoise2_FastNoise = new FastNoise(2);
randomNoise2_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise2_FastNoise.SetFrequency(0.0025);
var randomNoise2_ShortMap = WorldUtils.noise(randomNoise2_FastNoise, xSize,ySize);

var randomNoise3_FastNoise = new FastNoise(3);
randomNoise3_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise3_FastNoise.SetFrequency(0.005);
var randomNoise3_ShortMap = WorldUtils.noise(randomNoise3_FastNoise, xSize,ySize);

var randomNoise4_FastNoise = new FastNoise(4);
randomNoise4_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise4_FastNoise.SetFrequency(0.01);
var randomNoise4_ShortMap = WorldUtils.noise(randomNoise4_FastNoise, xSize,ySize);

var randomNoise5_FastNoise = new FastNoise(5);
randomNoise5_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise5_FastNoise.SetFrequency(0.02);
var randomNoise5_ShortMap = WorldUtils.noise(randomNoise5_FastNoise, xSize,ySize);

var randomNoise6_FastNoise = new FastNoise(6);
randomNoise6_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise6_FastNoise.SetFrequency(0.04);
var randomNoise6_ShortMap = WorldUtils.noise(randomNoise6_FastNoise, xSize,ySize);

var randomNoise7_FastNoise = new FastNoise(7);
randomNoise7_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise7_FastNoise.SetFrequency(0.08);
var randomNoise7_ShortMap = WorldUtils.noise(randomNoise7_FastNoise, xSize,ySize);

var randomNoise8_FastNoise = new FastNoise(8);
randomNoise8_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise8_FastNoise.SetFrequency(0.16);
var randomNoise8_ShortMap = WorldUtils.noise(randomNoise8_FastNoise, xSize,ySize);

var randomNoise9_FastNoise = new FastNoise(9);
randomNoise9_FastNoise.SetNoiseType(NoiseType.Simplex);
randomNoise9_FastNoise.SetFrequency(0.32);
var randomNoise9_ShortMap = WorldUtils.noise(randomNoise9_FastNoise, xSize,ySize);

var mountainNoise_FastNoise = new FastNoise(7);
mountainNoise_FastNoise.SetNoiseType(NoiseType.Simplex);
//mountainNoise_FastNoise.SetCellularDistanceFunction(CellularDistanceFunction.Manhattan);
//mountainNoise_FastNoise.SetCellularReturnType(CellularReturnType.Distance2);
mountainNoise_FastNoise.SetFrequency(0.002);


var mountainNoise_ShortMap = WorldUtils.noise(mountainNoise_FastNoise, xSize, ySize);
mountainNoise_ShortMap = WorldUtils.abs(mountainNoise_ShortMap, 0,0, xSize,ySize)
mountainNoise_ShortMap = WorldUtils.negate(mountainNoise_ShortMap, 0,0, xSize,ySize)


var noise_ShortMap_Array = new ShortMap_Array(10);
noise_ShortMap_Array[0] = randomNoise0_ShortMap;
noise_ShortMap_Array[1] = randomNoise1_ShortMap;
noise_ShortMap_Array[2] = randomNoise2_ShortMap;
noise_ShortMap_Array[3] = randomNoise3_ShortMap;
noise_ShortMap_Array[4] = randomNoise4_ShortMap;
noise_ShortMap_Array[5] = randomNoise5_ShortMap;
noise_ShortMap_Array[6] = randomNoise6_ShortMap;
noise_ShortMap_Array[7] = randomNoise7_ShortMap;
noise_ShortMap_Array[8] = randomNoise8_ShortMap;
noise_ShortMap_Array[9] = mountainNoise_ShortMap;

var noiseWeight_double_Array = new double_Array(10);
noiseWeight_double_Array[0] = 0.725;
noiseWeight_double_Array[1] = 0.515;
noiseWeight_double_Array[2] = 0.425;
noiseWeight_double_Array[3] = 0.375;
noiseWeight_double_Array[4] = 0.325;
noiseWeight_double_Array[5] = 0.25;
noiseWeight_double_Array[6] = 0.175;
noiseWeight_double_Array[7] = 0.125;
noiseWeight_double_Array[8] = 0.075;
noiseWeight_double_Array[9] = 1.0;


sum = WorldUtils.weightedAverage(noise_ShortMap_Array, noiseWeight_double_Array, 10, 0, 0, xSize, ySize);
sum = WorldUtils.threshold(sum,-0.2,0,0,xSize,ySize);

//sum = WorldUtils.fromDoubleStream(WorldUtils.toLossyDoubleStream(sum), xSize,ySize);

while(true)
{
	graphics.drawImage(sum.getImage(), 0,0,null);
}