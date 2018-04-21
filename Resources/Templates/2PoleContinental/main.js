//Fundamental stuff

var seed = 12;

var xSize = 1024;
var ySize = 1024;
var graphics = window.getGraphics();
//declare classes
var Math = Java.type("java.lang.Math");
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

function getShortMap(seed, xSize, ySize) {
	//set up the noise
	var cnoise = new FastNoise(seed+0); cnoise.SetNoiseType(NoiseType.Simplex);
	var mnoise = new FastNoise(seed+1); mnoise.SetNoiseType(NoiseType.Simplex);
	var rnoise = new FastNoise(seed+2); rnoise.SetNoiseType(NoiseType.Simplex);
	
	//set scales for continent noise and mountain noise
	var cscale = Math.pow(2, -9);
	var mscale = Math.pow(2, -8);
	
	//weights and sizes for 
	var sizes   = [Math.pow(2, -7), Math.pow(2, -8), Math.pow(2, -6), Math.pow(2, -5), Math.pow(2, -4), Math.pow(2, -2), Math.pow(2, -1)];
	var weights = [17,              15,              13,              10,              7,               5,               3              ]; 
	var weightsum = weights.reduce(function(a, b) { return a + b; }, 0);
	
	var map = new ShortMap(xSize, ySize);
	var shortmap = map.getMap();
	
	for(var y = 0; y < ySize; y++) {
		for(var x = 0; x < xSize; x++) {
			var cheight = cnoise.GetNoise(x*cscale, y*cscale);
			var mheight = 1-2*Math.abs(mnoise.GetNoise(x*mscale, y*mscale));
			
			//fractal noise...
			var rheight;
			for(var i = 0; i < sizes.length; i++) {
				rheight += weights[i]*rnoise.GetNoise(x*sizes[i], y*sizes[i]);
			}
			rheight = rheight/weightsum;
			
			var noiseSum = Math.pow(mheight*0.3 + rheight*0.4 + cheight*0.4, 3);
			shortmap[x][y] = OtherUtils.doubleToShort(noiseSum);
		}
	}
	return map;
}


var smap = getShortMap(seed,xSize,ySize);

while(true)
{
	graphics.drawImage(smap.getImage(), 0,0,null);
}