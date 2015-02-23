JBEE - Java Basic Expression Evaluator
--------------------------------------

JBEE is yet another parser and evaluator for arithmetic expressions. It 
started out as a calculator component for TradeTrax 
(http://www.onyxbits.de/tradetrax), but quickly matured into a standalone
project.

Rationale
---------

TradeTrax needed a way to evaluate simple, user submitted arithmetic 
expressions. A number of solutions already existed for that problem 
but turned out to be either buggy or have other shortcomings making
them unsuitable for the task. In the end it seemed easier to implement
a new evaluator, entirely from scratch, than to try project after
project, just to learn the hard way that they don't meet the 
requirements:

* A parser constructed from a BNF grammar to ensure that expressions
  evaluate properly.
* Leightweight, small sized code with no external runtime dependencies.
* Must use BigDecimal exclusively. Precission loss is not acceptable
  in library code (unless configured otherwise).
* Must be configurable to honor locale settings when parsing decimal 
  numbers.

For more information (in particular usage instructions), please see 
http://onyxbits.de/jbee/handbook
