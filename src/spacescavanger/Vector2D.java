/*
 * Teknikprogrammet, Malgomajskolan, Vilhelmina
 * Filen: Vector2D, Skapades: 13 dec. 2023
 */
package spacescavanger;

/**
 *
 * @author andberl08
 */
public class Vector2D {

    public double x, y;

    /**
     * Creates a 2D vector with given x and y values.
     *
     * @param x
     * @param y
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new 2D vector from another Vector.
     *
     * @param v The other vector.
     */
    public Vector2D(Vector2D v) {
        set(v);
    }

    /**
     * Sets the x and y values of the vector.
     *
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the vector's values based on another vector.
     *
     * @param v The other vector.
     */
    public void set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Sets the x and y values of the vector to zero (0).
     */
    public void setZero() {
        x = 0;
        y = 0;
    }

    /**
     * Calculates the length (distance from the origin) of the vector.
     *
     * @return The length of the vector
     */
    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the distance between this vector and another vector.
     *
     * @param vx The other vectors x-coordinate.
     * @param vy The other vectors y-coordinate.
     * @return The distance between the vector-coordinates. (not a vector).
     */
    public double distance(double vx, double vy) {
        vx -= x;
        vy -= y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    /**
     * Calculate the distance between this vector and another vector.
     *
     * @param v The other vector.
     * @return The distance between the vector-coordinates. (not a vector).
     */
    public double distance(Vector2D v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    /**
     * Gets the angle of the vector in relation to the x-axis.
     *
     * @return The angle in radians.
     */
    public double getAngle() {
        return Math.atan2(y, x);
    }

    /**
     * Gets a vector with the same direction but a length of 1.
     */
    public void normalize() {
        double magnitude = getLength();
        x /= magnitude;
        y /= magnitude;
    }

    /**
     * Gets a vector with the same direction but a length of 1.
     *
     * @return A normalized vector.
     */
    public Vector2D getNormalized() {
        double magnitude = getLength();
        return new Vector2D(x / magnitude, y / magnitude);
    }

    /**
     * Adds another vector to this vector.
     *
     * @param vx The other vectors x-coordinate.
     * @param vy The other vectors y-coordinate.
     */
    public void add(double vx, double vy) {
        this.x += vx;
        this.y += vy;
    }

    /**
     * Adds another vector to this vector.
     *
     * @param other The Other Vector.
     */
    public void add(Vector2D other) {
        this.set(new Vector2D(this.x + other.x, this.y + other.y));
    }

    /**
     * Adds and returns the result of another vector to this vector.
     *
     * @param v The other vector.
     * @return The new Vector.
     */
    public Vector2D getAdded(Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    /**
     * Subtracts another vector to this vector.
     *
     * @param v The other Vector
     */
    public void subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    /**
     * Subtracts another vector to this vector.
     *
     * @param vx The other vectors x-coordinate.
     * @param vy The other vectors y-coordinate.
     */
    public void subtract(double vx, double vy) {
        this.x -= vx;
        this.y -= vy;
    }

    /**
     * Subtracts and returns the result of another vector to this vector.
     *
     * @param v The other vector.
     * @return The new Vector.
     */
    public Vector2D getSubtracted(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    /**
     * Scales the vector by a given factor.
     *
     * @param factor The scale factor.
     */
    public void scale(double factor) {
        x *= factor;
        y *= factor;
    }

    /**
     * Scales and returns a new vector scaled by a given factor.
     *
     * @param factor The scale factor.
     * @return The scaled vector.
     */
    public Vector2D getScaled(double factor) {
        return new Vector2D(x * factor, y * factor);
    }

    /**
     * Divides the vector by a given factor.
     *
     * @param factor The division factor(nominator).
     */
    public void divide(double factor) {
        x /= factor;
        y /= factor;
    }

    /**
     * Divides and returns a new vector devided by a given factor.
     *
     * @param factor The scale factor(nominator).
     * @return The scaled vector.
     */
    public Vector2D getDivided(double factor) {
        return new Vector2D(x / factor, y / factor);
    }

    /**
     * Gets a vector perpendicular (90 degrees) to this vector.
     *
     * @return The perpendicular vector.
     */
    public Vector2D getPerp() {
        return new Vector2D(-y, x);
    }

    /**
     * Calculates the similarity of this vector and another vector in terms of
     * direction. If positive, they are pointing somewhat in the same direction.
     * If negative, they are pointing somewhat in opposite directions. If zero,
     * the vectors are perpendicular (at a right angle).
     *
     * @param v The other vector.
     * @return The dot product of the two vectors.
     */
    public double dot(Vector2D v) {
        return (this.x * v.x + this.y * v.y);
    }

    /**
     * Calculates the similarity of this vector and another vector in terms of
     * direction. If positive, they are pointing somewhat in the same direction.
     * If negative, they are pointing somewhat in opposite directions. If zero,
     * the vectors are perpendicular (at a right angle).
     *
     * @param vx The other vectors x-coordinate.
     * @param vy The other vectors y-coordinate.
     * @return The dot product of the two vectors.
     */
    public double dot(double vx, double vy) {
        return (this.x * vx + this.y * vy);
    }

    /**
     * Calculates a new vector perpendicular to the plane formed by this vector
     * and another vector. The magnitude indicates how much the two vectors are
     * tilted or twisted in relation to each other.
     *
     * @param v The other vector.
     * @return The cross product, a vector perpendicular to both input vectors.
     */
    public double cross(Vector2D v) {
        return (this.x * v.y - this.y * v.x);
    }

    /**
     * Calculates a new vector perpendicular to the plane formed by this vector
     * and another vector. The magnitude indicates how much the two vectors are
     * tilted or twisted in relation to each other.
     *
     * @param vx The other vectors x-coordinate.
     * @param vy The other vectors y-coordinate.
     * @return The cross product, a vector perpendicular to both input vectors.
     */
    public double cross(double vx, double vy) {
        return (this.x * vy - this.y * vx);
    }

    /**
     * Calculates the projection of this vector onto another vector. The
     * projection represents the shadow of this vector onto the specified
     * vector. It helps to understand how much of this vector points in the same
     * direction as the other vector.
     *
     * @param v The vector onto which this vector will be projected.
     * @return The scalar value representing the length of the projection.
     */
    public double project(Vector2D v) {
        return (this.dot(v) / this.getLength());
    }

    /**
     * Calculates the projection of this vector onto another vector. The
     * projection represents the shadow of this vector onto the specified
     * vector. It helps to understand how much of this vector points in the same
     * direction as the other vector.
     *
     * @param vx The x-coordinate of the vector onto which this vector will be
     * projected.
     * @param vy The y-coordinate of the vector onto which this vector will be
     * projected.
     * @return The length of the projection.
     */
    public double project(double vx, double vy) {
        return (this.dot(vx, vy) / this.getLength());
    }

    public Vector2D getProjectedVector(Vector2D v) {
        return this.getNormalized().getScaled(this.dot(v) / this.getLength());
    }

    public Vector2D getProjectedVector(double vx, double vy) {
        return this.getNormalized().getScaled(this.dot(vx, vy) / this.getLength());
    }

    /**
     * Rotates the vector by a given angle.
     *
     * @param angle The angle in radians.
     */
    public void rotateBy(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double rx = x * cos - y * sin;
        y = x * sin + y * cos;
        x = rx;
    }

    /**
     * Rotates and return the rotated vector by a given angle.
     *
     * @param angle The angle in radians.
     * @return A rotated vector.
     */
    public Vector2D getRotatedBy(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector2D(x * cos - y * sin, x * sin + y * cos);
    }

    /**
     * Reverses the direction of the vector.
     */
    public void reverse() {
        x = -x;
        y = -y;
    }

    /**
     * Reverses the direction of the vector and returns the results.
     *
     * @return A reversed vector.
     */
    public Vector2D getReversed() {
        return new Vector2D(-x, -y);
    }

    /**
     * Clone the current vector.
     *
     * @return A new vector that is a copy of this.
     */
    public Vector2D cloneVector2D() {
        return new Vector2D(x, y);
    }

    /**
     * Check if this vector equals another object.
     *
     * @param obj The other object.
     * @return True if equal, False if not.
     */
    public boolean equalsVector2D(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vector2D) {
            Vector2D v = (Vector2D) obj;
            return (x == v.x) && (y == v.y);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vector2D[" + x + ", " + y + "]";
    }

}
