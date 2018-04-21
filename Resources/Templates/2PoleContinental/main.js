//Fundamental stuff

var seed = 120;

var xSize = 1024;
var ySize = 1024;
var graphics = window.getGraphics();
//declare classes
var Math = Java.type("java.lang.Math");
var Short = Java.type("java.lang.Short");
var Integer = Java.type("java.lang.Integer");
var Long = Java.type("java.lang.Long");
var Double = Java.type("java.lang.Double");
var int_Array = Java.type("int[]");
var double_Array = Java.type("double[]");
var double_Array2D = Java.type("double[][]");
var short_Array2D = Java.type("short[][]");

var FastNoise = Java.type("fastnoise.FastNoise");
var NoiseType = Java.type("fastnoise.FastNoise$NoiseType");
var FractalType = Java.type("fastnoise.FastNoise$FractalType");
var CellularDistanceFunction = Java.type("fastnoise.FastNoise$CellularDistanceFunction");
var CellularReturnType = Java.type("fastnoise.FastNoise$CellularReturnType");

var WorldUtils = Java.type("worldUtils.WorldUtils");
var OtherUtils = Java.type("worldUtils.OtherUtils");
var ShortMap = Java.type("worldUtils.ShortMap");
var ShortMap_Array = Java.type("worldUtils.ShortMap[]");

function getHeightMap(seed, xSize, ySize) {
	//set up the noise
	var cnoise = new FastNoise(seed+0); cnoise.SetNoiseType(NoiseType.Simplex);
	var mnoise = new FastNoise(seed+1); mnoise.SetNoiseType(NoiseType.Simplex);
	var rnoise = new FastNoise(seed+2); rnoise.SetNoiseType(NoiseType.Simplex);
	
	//set scales for continent noise and mountain noise
	var cscale = Math.pow(2, -2);
	var mscale = Math.pow(2, -2);
	
	//weights and sizes for 
	var sizes   = [Math.pow(2, -1), Math.pow(2,  0), Math.pow(2,  1), Math.pow(2,  2), Math.pow(2,  3), Math.pow(2,  4), Math.pow(2, 5)];
	var weights = [17,              15,              13,              10,              7,               5,               3             ]; 
	var weightsum = weights.reduce(function(a, b) { return a + b; }, 0);
	
	var map = new ShortMap(xSize, ySize);
	var shortmap = map.getMap();
	
	for(var y = 0; y < ySize; y++) {
		for(var x = 0; x < xSize; x++) {
			var cheight = cnoise.GetNoise(x*cscale, y*cscale);
			var mheight = Math.pow(1-2*Math.abs(mnoise.GetNoise(x*mscale, y*mscale)),3)-0.2;
			
			//fractal noise...
			var rheight = -0.1;
			for(var i = 0; i < sizes.length; i++) {
				rnoise.SetSeed(seed+2+i);
				rheight += weights[i]*rnoise.GetNoise(x*sizes[i], y*sizes[i]);
			}
			rheight = rheight/weightsum;
			
			var noiseSum = mheight*0.2 + rheight*0.5 + cheight*0.3;
			//print(cheight);
			shortmap[x][y] = OtherUtils.doubleToShort(noiseSum);
		}
	}
	return map;
}

//returns surface temperature. Temperature shall be measured from (Short.MIN_VALUE, Short.MAX_VALUE) (0 = freezing) (1000 = boiling) (Temp at sea level The actual temperature is determined by simple subtraction) 
function getTemperature(seed, xSize, ySize) {
	
	var noise = new FastNoise(seed);
	
	var scale = Math.pow(2,-4);
	var weight = 0.3;
	
	var map = new ShortMap(xSize, ySize);
	var shortmap = map.getMap();
	
	for(var y = 0; y < ySize; y++) {
		for(var x = 0; x < xSize; x++) {
			shortmap[x][y]
		}
	}
	
}



var smap = getShortMap(Math.random()*200,xSize,ySize);

smap = WorldUtils.threshold(smap, 0, 0,0,xSize,ySize);

while(true)
{
	graphics.drawImage(smap.getImage(), 0 ,0,null);
}