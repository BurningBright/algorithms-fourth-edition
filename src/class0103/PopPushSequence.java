package class0103;
/**
 * a. 4 3 2 1 0 9 8 7 6 5 
 * b. 4 6 8 7 5 3 2 9 0 1 
 * c. 2 5 6 7 4 8 9 3 1 0 
 * d. 4 3 2 1 0 5 6 7 8 9 
 * e. 1 2 3 4 5 6 9 8 7 0 
 * f. 0 4 6 5 3 8 1 7 2 9 
 * g. 1 4 7 9 8 6 5 3 0 2 
 * h. 2 1 4 3 6 5 8 7 9 0
 * @author soft01
 *
 */
public class PopPushSequence {
	/*
	 * a- push 01234 pop 43210 push56789 pop 98765
	 * 
	 * b- push 01234 pop 4 push 56 pop 6 push 78 pop
	 * 		87532 push 9 pop	-not occur
	 * 
	 * c- push 012 pop 2 push 345 pop 5 push 6 pop 6
	 * 		push 7 pop 74 push 8 pop 8 push 9 pop 9310
	 * 
	 * d- push 01234 pop 43210 push 5 pop 5 …… pop9
	 * 
	 * e- push 01 pop 1 push 2 pop 2 …… pop6 push 789
	 * 		pop 9870
	 * 
	 * f- push 0 pop 0 push 1234 pop 4 push 56 pop 653
	 * 		push 78 pop 8	-not occur
	 * 
	 * g- push 01 pop 1 push 234 pop 4  push 567 pop 7
	 * 		push 89 pop 98653		-not occur
	 * 
	 * h- push 012 pop 21 push 34 pop 43 push 56 pop
	 * 		65 push 78 pop 87 push 9 pop 90
	 * 
	 */
}
