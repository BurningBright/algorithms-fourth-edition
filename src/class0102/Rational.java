package class0102;

/**
 * supports addition, subtraction,
 *  multiplication, and division.
 * @author soft01
 *
 */
public class Rational implements Comparable<Rational>{
	private int numerator;
	private int denominator;

	public Rational(int n, int d) {
		if(d==0) {
			throw new RuntimeException();
		}
		this.numerator = n;
		this.denominator = d;
	}
	
	public Rational plus(Rational b) {
		int utilDen = this.denominator*b.denominator;
		int sumNum = this.numerator*b.denominator +b.numerator*this.denominator;
		return new Rational(sumNum, utilDen);
	}
	
	public Rational minus(Rational b) {
		int utilDen = this.denominator*b.denominator;
		int sumNum = this.numerator*b.denominator - b.numerator*this.denominator;
		return new Rational(sumNum, utilDen);
	}
	
	public Rational times(Rational b) {
		return new Rational(this.numerator*b.numerator, this.denominator*b.denominator);
	}
	
	public Rational divides(Rational b) {
		return new Rational(this.numerator*b.denominator, this.denominator*b.numerator);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rational other = (Rational) obj;
		int utilNum = numerator*other.denominator;
		int utilDen = denominator*other.numerator;
		if (utilNum != utilDen) {
			return false;
		}
		return true;
	}
	
	@Override
	public int compareTo(Rational o) {
		Rational o1 = this.minus(o);
		return o1.numerator* o1.denominator;
	}
	
	public String toString() {
		return "Rational [numerator=" + numerator + ", denominator="
				+ denominator + "]\r";
	}
	
	public static void main(String[] args) {
		Rational r1 = new Rational(2,5);
		Rational r2 = new Rational(2,5);
		System.out.print(r1.divides(r2).toString());
		System.out.print(r1.equals(r2));
	}


}
