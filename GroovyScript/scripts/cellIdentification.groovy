import qupath.lib.gui.measure.ObservableMeasurementTableData


/**
 * Rename all cells from each annotation hierarchy for better ID
 * Print out defined measurements of each cell
 */
private def cellIdentification() {
   // Get children annotations of hierarchy
    def children = null
    getAnnotationObjects().each {
        children = it.getChildObjects()
    }
    
    // Rename children annotations for better ID
    int cell_index = 1
    for (int i; i < children.size(); i++) { 
        new_child = 'cell_' + cell_index
        children[i].setName(new_child)
        print(children[i].getName())
        println "${children[i].getROI().getGeometry()}}"
        cell_index++
    } 
}


/**
 * Check for adjacent cells of selected cell
 * Adjacent cells in list
 */
private def checkAdjacent(Object cell) {
   
}
   

/**
 * Inserts 5 Line-annotations between random cell membranes
 * Prints out the measurements of thickness of each annotation
 */
private def insertThicknessMeasurement(Object obj) {
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
}    


/**
 * Main
 */
public static void main(String[] args) {
    cellIdentification()
}