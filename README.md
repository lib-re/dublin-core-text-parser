**Purpose**

_Short_:
Convert dublin core metadata stored in text files to a machine-readable
  JSON object to be used by other software.

_Long_:
Assist in cataloguing batches of similar or series-based items from a
collection by:
 -decreasing the complexity of logging each individual item/issue
 -minimizing repetitive tying and template updating
 -combining the information that is shared across collections

**Usage Instructions**

1. _Create Text File/s_
Create a text file of the basic metadata for each issue in the collection.
2. _Edit Config Files_
Edit the settings to include any and all shared metadata applicable to all
  of the issues in the given batch (e.g language, publisher, etc.)
3. _Run Script_
Run the script to create a JSON object from each of these text files.
4. _Check and Utilize Output_
Using the actual json objects is out of scope for this program.
5. _Clean up or Reference Text Files_
After completion of the above tasks, the text files can be discarded as
  irrelevant, or used as a quick reference to the metadata info along-
  side where you're storing the files themselves.

**Notes**

_Uploading Original Files_
-If you'd like to upload the original files alongside cataloguing them,
 include the filename with its tag and ensure that they are located in
 the same folder as the digital item you are trying to upload.

_Collection/Grouping Determination_
-I've made how you determine how you batch the uploads entirely open to
 the user. For our purposes, I plan to use it according to the calendar
 year (~15-25 issues) but organization by volume or else would work too.

**Background**
This program was written to assist with my work in cataloguing and uploading
  a collection of magazines into our DSpace database. I found working with
  the web interface we were using laborious and inefficient, and the OCR
  scan very helpful, though occasionally faulty.

Sticking to the plan, I had to input each name one-by-one, and choose
  whether I wanted to be constantly re-typing names, or using a template
  and removing ones that were not relevant issue to issue. Both options
  introduced the potential for a lot of errors, took a lot of time, and
  failed to leverage the OCR sufficiently.

My alternative protocol suggests that very basic data (e.g. contributors and
  article titles) be inputted into a text file according to some basic
  heading types in bulk (all issues in a given collection)

This basic script is meant as the first step in the move from an OCR-ed pdf
  into a metadata holding format usable by both


**Rationale**

_Json (and not MARC)_
  -growing universal standard for encoded machine-readable data
  -easily transferable and lightweight
  -human (and non-professional) readability
  -integration with web (and micro-) services
  -easy to learn/intuit with little to no pre-requisite knowledge

_Configuration //Todo__
 -portability/customization (many collection-specific tweaks)
 -ease of use (decrease pre-requisites for use)
 -speed (faster to tweak a config than to fork and edit code)


**External Links**
Dublin Core documentation: http://dublincore.org/documents/dces/
Sample Dublin Core JSON object: https://www.w3.org/2008/WebVideo/Annotations/drafts/API10/JSON/normative_json_ma_dc.json
