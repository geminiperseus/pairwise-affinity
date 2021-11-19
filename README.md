# pairwise-affinity

1. Imagine you are data infrastructure engineer at a restaurant
   reservation booking platform company such as OpenTable, Resy,
   Yelp Reservations, or Tock.  One of your team's responsibilities
   is providing data analytics to assist the Machine Learning team
   with developing new intelligent functionality.
 
   The ML team wants to build a restaurant recommendation service
   as part of the platform.  One feature that they want to include
   in the training data set is called the "Pair-Wise Restaurant
   Affinity Score".
 
   The Pair-Wise Restaurant Affinity Score is defined as follows:
   For each restaurant X, define a count AS of how many times a
   customer, C, for all C, visited both restaurants X and Y for
   all restaurants Y.
 
   The idea is to track how often pairs of restaurants were visited by
   the same customer as a metric of which restaurants make better
   recommendations for customers based on other restaurants they
   visited.
 
   * Calculate the Pair-Wise Restaurant Affinity Score for all
        restaurants in the system.
 
  NOTES:
   - The MapReduce framework provided is a Fake, it mimicks the behavior
     of a real Big Data process but uses regular Java and runs the
     Mappers and Reducers in for loops rather than separate threads.
   - An abstract MapReduce class is provided as the basis MR implemention.  
   - All the fields are  public, se we don't have to worry about getters
     and setters.