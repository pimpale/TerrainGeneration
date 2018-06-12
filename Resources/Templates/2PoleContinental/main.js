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
var Stream = Java.type("java.util.stream.Stream");

var FastNoise = Java.type("fastnoise.FastNoise");
var NoiseType = Java.type("fastnoise.FastNoise$NoiseType");
var FractalType = Java.type("fastnoise.FastNoise$FractalType");
var CellularDistanceFunction = Java.type("fastnoise.FastNoise$CellularDistanceFunction");
var CellularReturnType = Java.type("fastnoise.FastNoise$CellularReturnType");

var WorldUtils = Java.type("worldUtils.WorldUtils");
var OtherUtils = Java.type("worldUtils.OtherUtils");
var HeightMap = Java.type("worldUtils.HeightMap");
var Height = Java.type("worldUtils.Height");

function getHeightMap(seed, xSize, ySize) {
	//set up the noise
	var mnoise = new FastNoise(seed+1); mnoise.SetNoiseType(NoiseType.SimplexFractal); mnoise.SetFractalOctaves(8); mnoise.SetFrequency(Math.pow(2,-2));
	var cnoise = new FastNoise(seed+2); cnoise.SetNoiseType(NoiseType.SimplexFractal); cnoise.SetFractalOctaves(8); cnoise.SetFrequency(Math.pow(2,-5))
	var rnoise = new FastNoise(seed+3); rnoise.SetNoiseType(NoiseType.SimplexFractal); rnoise.SetFractalOctaves(8); rnoise.SetFrequency(Math.pow(2,-4))
	//set scales for continent noise and mountain noise

	var map = new HeightMap(xSize, ySize);
	map = map
			.stream()
			.map(function(h) {
				var x = h.getX();
				var y = h.getY();
				//var mheight = Math.pow(1-3*Math.abs(mnoise.GetNoise(x*0.05, y*0.05)),3);
				var rheight = rnoise.GetNoise(x, y);
				var fheight = rheight;
				h.setVal(OtherUtils.clamp(fheight,-1,1));
				return h;
			})
			.collect(HeightMap.getCollector());
	map = WorldUtils.fillBasins(map,-0.2);
	return map;
}

//returns surface temperature. Temperature shall be measured in celsius
function getTemperatureMap(seed, xSize, ySize) {
	var noise = new FastNoise(seed);
	noise.SetNoiseType(NoiseType.SimplexFractal);
	noise.SetFractalOctaves(10);
	var scale = Math.pow(2,-3);
	return new HeightMap(xSize,ySize)
				.stream()
				.map(function(h) {
					var percentDown = h.getY()/ySize;
					var latTemp = 60*(Math.sin(Math.PI*percentDown)-0.5);
					var randTemp = 40*noise.GetNoise(x*scale,y*scale);
					h.setVal(latTemp + randTemp)
					return h;
				})
				.collector(HeightMap.getCollector());
			
}


//var tmap = getTemperatureMap(seed,xSize,ySize);
var smap = getHeightMap(seed,xSize,ySize);
smap = new HeightMap(smap.stream().map(function(height) {
	if(height.val < 0) {
		height.val = -1;
	}
	return height;
}));
var img = smap.getImage();
while(true)
{
	Thread.sleep(20);
	graphics.drawImage(img, 0 ,0,null);
}