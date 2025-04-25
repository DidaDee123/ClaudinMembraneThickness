import qupath.lib.gui.measure.ObservableMeasurementTableData

int z = 0
int t = 0
def plane = ImagePlane.getPlane(z, t)
def roi = ROIs.createLineROI(21450.83, 14484.25, 21449.41, 14483.4, plane)
//def roi = ROIs.createLineROI(21445.529, 14490.29, 0, 0, plane)
def annotation = PathObjects.createAnnotationObject(roi)
annotation.setName("Thickness_1")
color = getColorRGB(0, 100, 100)
annotation.setColor(color)
addObject(annotation)

def ob = new ObservableMeasurementTableData();
def annotations = getAnnotationObjects()
ob.setImageData(getCurrentImageData(),  annotations);
int index = 0
annotations.each { 
    length=ob.getNumericValue(it, "Length µm")
    print "Thickness_${index}: ${length}µm"
    index++
}