### Description
I've implemented the solution sequentially, but I also know a concurrent, pipelined solution that should work significantly better for shipping large order from a wide selection of warehouses. It works as follows:

Suppose you have 5 warehouses and 5 items in an order; since by assumption, the warehouse closer to the beginning of the warehouse array is cheaper to ship, we need to check the first warehouse before moving on to the second.
1. First, use processor 1 to check if warehouse 1 can fulfill item 1
2. If not, then use processor 2 to check if warehouse 2 can fulfill item 1; at the same time, bring in item 2 to the pipeline--use processor 1 to check if warehouse 1 can fulfill item 2
3. Items at the back of the pipeline can only move forward when the next processor becomes available;so when item 1 is on processor 3, item 2 has to stay in processor 2.
4. The pipeline continues until all items find their stock in the warehouse,
or throws an error if an item can't be fulfilled.
5. If an item reaches the last processor but the number of warehouses is bigger than the number of processors, then an new item won't enter the pipeline; instead, the item on the last processor will move on to the first processor.

This approach should be significantly faster for shipping large order from a wide selection of warehouses; but since I don't have the time to hand-type such orders unless I'm hired, and since having an untested concurrent system is same as having nothing,I only implemented a draft of the concurrent code (and part of it hasn't been synchronized yet)

The sequential, single-thread solution is complete and tested on some benchmark test cases.
Hire me and I'll finish & test the concurrent code ;)

Jokes aside, I know Deliverr have much better systems and I do like to work with y'all

### Test cases
I've written test cases that covers both simple and complex cases, including cases with invalid inputs. The result shows that my code works.
To run the test cases, open the project in Intellij, add junit 4 to the path, and hit the green run button.
