//Fundamental stuff
var xSize = window.getWidth();
var ySize = window.getHeight();
var graphics = window.getGraphics();
//declare classes
var Math = Java.type("java.lang.Math");
var Short = Java.type("java.lang.Short");
var Integer = Java.type("java.lang.Integer");
var Long = Java.type("java.lang.Long");
var Double = Java.type("java.lang.Double");
var Thread = Java.type("java.lang.Thread");
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
	var mnoise = new FastNoise(seed+1); mnoise.SetNoiseType(NoiseType.SimplexFractal); mnoise.SetFractalOctaves(8);
	var rnoise = new FastNoise(seed+2); rnoise.SetNoiseType(NoiseType.SimplexFractal); rnoise.SetFractalOctaves(8);
	
	//set scales for continent noise and mountain noise
	var mscale = Math.pow(2, -1);
	var rscale = Math.pow(2, -2);
	var map = new ShortMap(xSize, ySize);
	var shortmap = map.getMap();
	
	for(var y = 0; y < ySize; y++) {
		for(var x = 0; x < xSize; x++) {
			var mheight = Math.pow(1-2*Math.abs(mnoise.GetNoise(x*mscale, y*mscale)),3)-0.3;
			var rheight = rnoise.GetNoise(x*rscale,y*rscale)*1.35;
			var noiseSum = mheight*0.4 + rheight*0.6;
			shortmap[x][y] = OtherUtils.doubleToShort(noiseSum);
		}
	}
	return map;
}

//returns surface temperature. Temperature shall be measured from (Short.MIN_VALUE, Short.MAX_VALUE) (0 = freezing) (1000 = boiling) (Temp at sea level The actual temperature is determined by simple subtraction) 
function getTemperatureMap(seed, xSize, ySize) {
	
	var noise = new FastNoise(seed);
	noise.SetNoiseType(NoiseType.SimplexFractal);
	noise.SetFractalOctaves(10);
	var scale = Math.pow(2,-3);
	var map = new ShortMap(xSize, ySize);
	var shortmap = map.getMap();
	
	for(var y = 0; y < ySize; y++) {
		var percentDown = y/ySize;
		for(var x = 0; x < xSize; x++) {
			var celsius = 60*(Math.sin(Math.PI*percentDown)-0.5) + 40*noise.GetNoise(x*scale,y*scale);
			shortmap[x][y] = OtherUtils.celsiusToTemperature(celsius);
		}
	}
	return map;
}


var tmap = getTemperatureMap(seed,xSize,ySize);
//var smap = getHeightMap(seed,xSize,ySize);
//smap = WorldUtils.threshold(smap, 0, 0,0,xSize,ySize);

var img = tmap.getImage();
while(true)
{
	Thread.sleep(20);
	graphics.drawImage(img, 0 ,0,null);
}