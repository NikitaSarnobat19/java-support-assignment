Task 2: ConcurrentModificationException

1. Exact cause: ArrayList tracks a modCount that increments on structural changes. An iterator captures modCount on creation and checks it on every next(). If the list is modified directly (not via the iterator) while iterating, the counts mismatch and ConcurrentModificationException is thrown. It's a fail-fast check, not a real thread-safety mechanism.

2. Likely pattern at line 142: A for-each loop over the transaction list that calls list.remove(...) directly inside the loop (e.g. filtering out invalid transactions), instead of removing via the iterator.

3. Minimal fix (1 line):
transactions.removeIf(t -> !isValid(t));
(Equivalent alternative: use an explicit Iterator and call iterator.remove() instead of list.remove().)