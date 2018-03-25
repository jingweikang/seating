# Seating
Program to automate seating for a student networking dinner.

Made by Jingwei Kang 2018.

## Motivation
The student networking dinner that this program was written for features multiple
rotations in which students are seated at a table with company representatives from a given company. 
A student may not sit with the same company on subsequent rotations. Students submit
preference lists, which are processed on a first-come-first-serve basis.

## Prerequisites
1. Obtain a preference list from each student. Every available company should be on each preference list.
2. Determine the maximum number of students that may sit with each company for any given rotation.
3. Format the inputs for students and companies as csv files similar to SampleStudents.csv and SampleCompanies.csv, respectively.
4. Create an empty csv file to output the seating on.

## Usage
Run the main method in Seating.java. The method will prompt for the input and output file names in addition to the number of rotations.

A sample of seating outputs is available in SampleFinal.csv. The seating is first outputted with a new row for each student, 
and then by rotation for each table.

#### Possible Issues ####
**Note**: The last couple of students do not have seating for later rotations.
This is because the program runs on a stable-marriage-like algorithm, but is given unbalanced preference lists
(the preference list for companies is essentially the order in which students respond and their preferences are processed).
The last student responders essentially "propose" last and are also last on the preference lists for each company.
Given that students may not sit with repeating companies, there may not be solutions in later rotations.

This issue is likely to arise if certain companies are very high or low on the preference lists of many students.
This can be resolved in two ways:  
1. Increase the maximum load of each company as seen in SampleCompanies.csv. However, if preference lists are unbalanced 
as noted above, this will lead to unbalanced seating (where companies that are lower on preference lists may sit with
few students).
2. Alternatively, input maximum loads that are lower than what is actually possible. This will keep tables balanced but
will require some manual changes at the end of the output. 
