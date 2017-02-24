# logibeat-commons-geography
point in polygon, circle - Check if the shape (or "enclosure") contains the given point effective and efficient.

In geographic case, the shape may be a simple polygon, or a circle, or a complicated polygon with holes.
The value of point's x is between -180.000000 and 180.000000, while the value of point'y is between -90.000000 and 90.000000.

Many thanks to Roman Kushnarenko who is the author of [romku/polygon-contains-point] 
(https://github.com/logibeat/polygon-contains-point)

When the enclosure is a simple polygon, we find the <code>JDK Algorithm Policy</code> is the best one so far.
