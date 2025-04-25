import qupath.lib.gui.measure.ObservableMeasurementTableData



private def smoothen(String annoName) {
    rings = getAnnotationObjects()
    selectAnnotations()
    runPlugin('qupath.lib.plugins.objects.DilateAnnotationPlugin', '{"radiusMicrons": 15.0,  "lineCap": "Round",  "removeInterior": false,  "constrainToParent": false}');
    removeObjects(rings, true)
    selectAnnotations()
    rings = getAnnotationObjects()
    runPlugin('qupath.lib.plugins.objects.DilateAnnotationPlugin', '{"radiusMicrons": -15.0,  "lineCap": "Round",  "removeInterior": false,  "constrainToParent": false}');
    removeObjects(rings,true)
    resetSelection()

}


/**
 * 
 */
private def cellDetection() {
   getAnnotationObjects().each {
       if (it.getName() =='Annotation') {
           
       }
           
   }
}

/**
 * Rename all cells from each annotation hierarchy for better ID
 * Print out defined measurements of each cell
 */
private def cellIdentification() {
   // Get children annotations of hierarchy
    def children = []
    int annoIndex = 0
    getAnnotationObjects().each {
        children[annoIndex] = it.getChildObjects()
        annoIndex++
    }
    
    // Rename children annotations for better ID
    for (int j = 0; j < children.size(); j++) {
        println "${getAnnotationObjects()[j].getName()}:"
        
        int cell_index = 1
        for (int i = 0; i < children[j].size(); i++) { 
            new_child = 'cell_' + cell_index
            children[j][i].setName(new_child)
            println "${children[j][i].getName()}:"
            println "${children[j][i].getROI().getGeometry()}"
            cell_index++
        }
        println ""
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