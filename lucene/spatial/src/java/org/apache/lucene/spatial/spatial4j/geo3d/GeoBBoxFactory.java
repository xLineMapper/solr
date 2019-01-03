package org.apache.lucene.spatial.spatial4j.geo3d;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Factory for {@link org.apache.lucene.spatial.spatial4j.geo3d.GeoBBox}.
 *
 * @lucene.experimental
 */
public class GeoBBoxFactory {
  private GeoBBoxFactory() {
  }

  /**
   * Create a geobbox of the right kind given the specified bounds.
   *
   * @param topLat    is the top latitude
   * @param bottomLat is the bottom latitude
   * @param leftLon   is the left longitude
   * @param rightLon  is the right longitude
   * @return a GeoBBox corresponding to what was specified.
   */
  public static GeoBBox makeGeoBBox(double topLat, double bottomLat, double leftLon, double rightLon) {
    //System.err.println("Making rectangle for topLat="+topLat*180.0/Math.PI+", bottomLat="+bottomLat*180.0/Math.PI+", leftLon="+leftLon*180.0/Math.PI+", rightlon="+rightLon*180.0/Math.PI);
    if (topLat > Math.PI * 0.5)
      topLat = Math.PI * 0.5;
    if (bottomLat < -Math.PI * 0.5)
      bottomLat = -Math.PI * 0.5;
    if (leftLon < -Math.PI)
      leftLon = -Math.PI;
    if (rightLon > Math.PI)
      rightLon = Math.PI;
    if (Math.abs(leftLon + Math.PI) < Vector.MINIMUM_RESOLUTION && Math.abs(rightLon - Math.PI) < Vector.MINIMUM_RESOLUTION) {
      if (Math.abs(topLat - Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION && Math.abs(bottomLat + Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION)
        return new GeoWorld();
      if (Math.abs(topLat - bottomLat) < Vector.MINIMUM_RESOLUTION) {
        if (Math.abs(topLat - Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION || Math.abs(topLat + Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION)
          return new GeoDegeneratePoint(topLat, 0.0);
        return new GeoDegenerateLatitudeZone(topLat);
      }
      if (Math.abs(topLat - Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION)
        return new GeoNorthLatitudeZone(bottomLat);
      else if (Math.abs(bottomLat + Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION)
        return new GeoSouthLatitudeZone(topLat);
      return new GeoLatitudeZone(topLat, bottomLat);
    }
    //System.err.println(" not latitude zone");
    double extent = rightLon - leftLon;
    if (extent < 0.0)
      extent += Math.PI * 2.0;
    if (topLat == Math.PI * 0.5 && bottomLat == -Math.PI * 0.5) {
      if (Math.abs(leftLon - rightLon) < Vector.MINIMUM_RESOLUTION)
        return new GeoDegenerateLongitudeSlice(leftLon);

      if (extent >= Math.PI)
        return new GeoWideLongitudeSlice(leftLon, rightLon);

      return new GeoLongitudeSlice(leftLon, rightLon);
    }
    //System.err.println(" not longitude slice");
    if (Math.abs(leftLon - rightLon) < Vector.MINIMUM_RESOLUTION) {
      if (Math.abs(topLat - bottomLat) < Vector.MINIMUM_RESOLUTION)
        return new GeoDegeneratePoint(topLat, leftLon);
      return new GeoDegenerateVerticalLine(topLat, bottomLat, leftLon);
    }
    //System.err.println(" not vertical line");
    if (extent >= Math.PI) {
      if (Math.abs(topLat - bottomLat) < Vector.MINIMUM_RESOLUTION) {
        //System.err.println(" wide degenerate line");
        return new GeoWideDegenerateHorizontalLine(topLat, leftLon, rightLon);
      }
      if (Math.abs(topLat - Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION) {
        return new GeoWideNorthRectangle(bottomLat, leftLon, rightLon);
      } else if (Math.abs(bottomLat + Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION) {
        return new GeoWideSouthRectangle(topLat, leftLon, rightLon);
      }
      //System.err.println(" wide rect");
      return new GeoWideRectangle(topLat, bottomLat, leftLon, rightLon);
    }
    if (Math.abs(topLat - bottomLat) < Vector.MINIMUM_RESOLUTION) {
      if (Math.abs(topLat - Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION || Math.abs(topLat + Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION) {
        return new GeoDegeneratePoint(topLat, 0.0);
      }
      //System.err.println(" horizontal line");
      return new GeoDegenerateHorizontalLine(topLat, leftLon, rightLon);
    }
    if (Math.abs(topLat - Math.PI * 0.5) < Vector.MINIMUM_RESOLUTION) {
      return new GeoNorthRectangle(bottomLat, leftLon, rightLon);
    } else if (Math.abs(bottomLat + Math.PI * 0.5) <  Vector.MINIMUM_RESOLUTION) {
      return new GeoSouthRectangle(topLat, leftLon, rightLon);
    }
    //System.err.println(" rectangle");
    return new GeoRectangle(topLat, bottomLat, leftLon, rightLon);
  }

}
