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
	var mnoise = new FastNoise(seed+1); mnoise.SetNoiseType(NoiseType.SimplexFractal); mnoise.SetFractalOctaves(8);
	var cnoise = new FastNoise(seed+2); cnoise.SetNoiseType(NoiseType.SimplexFractal);
	var rnoise = new FastNoise(seed+3); rnoise.SetNoiseType(NoiseType.SimplexFractal); rnoise.SetFractalOctaves(8);
	//set scales for continent noise and mountain noise

	var cscale = Math.pow(2, -3);
	var mscale = Math.pow(2, -2);
	var rscale = Math.pow(2, -1);
	var map = new HeightMap(xSize, ySize);
	map = new HeightMap(map
			.stream()
			.map(function(h) {
				var x = h.getX();
				var y = h.getY();
				var mheight = Math.pow(1-3*Math.abs(mnoise.GetNoise(x*mscale, y*mscale)),1);
				var cheight = cnoise.GetNoise(x*cscale,y*cscale);
				var rheight = rnoise.GetNoise(x*rscale,y*rscale);
				var fheight = cheight*0.5 + rheight*0.3 + mheight*0.2 - 0.1;
				h.setVal(OtherUtils.clamp(fheight,-1,1));
				return h;
			}));
	map = WorldUtils.fillBasins(map,-0.2);
	map = new HeightMap(map
			.stream()
			.map(function(h) {
				var x = h.getX();
				var y = h.getY();
				h.setVal(h.getVal() + 0*h.getVal()*mnoise.GetNoise(2*x,2*y));
				return h;
			}));
	return map;
}

//returns surface temperature. Temperature shall be measured in celsius
function getTemperatureMap(seed, xSize, ySize) {
	var noise = new FastNoise(seed);
	noise.SetNoiseType(NoiseType.SimplexFractal);
	noise.SetFractalOctaves(10);
	var scale = Math.pow(2,-3);
	var map = new HeightMap(xSize, ySize);
	return new HeightMap(
			new HeightMap(xSize,ySize)
				.stream()
				.map(function(h) {
					var percentDown = h.getY()/ySize;
					var latTemp = 60*(Math.sin(Math.PI*percentDown)-0.5);
					var randTemp = 40*noise.GetNoise(x*scale,y*scale);
					h.setVal(latTemp + randTemp)
					return h;
				}
			)
		);
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