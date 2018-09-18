### Purpose

_Short_:
Convert dublin core metadata stored in text files to other machine-readable
  formats to be used by other software.

_Long_:
Assist in cataloguing batches of similar or series-based items from a collection by:
* decreasing the complexity of logging each individual item/issue
* minimizing repetitive typing and template editing
* combining the information that is shared across items in a collection in one place

## Usage

### Process Instructions

1. _Edit Config File_: 
If desired, edit the configuration file to customize the format of the header
2. _Edit Shared File_:
Edit the settings to include any and all shared metadata applicable to all
  of the items in the given batch (e.g language, publisher, etc.)
3. _Create Text File/s_: 
Create a text file of the basic metadata for each item in the collection.
4. _Run Script_:
Run the script to create, in that directory, the desired output/s encoded with
  the dublin-core metadata you logged in the text files. 
5. _Check and Utilize Output_:
Ensure that everything has been placed in the appropriate field by checking a 
  few individual item representations. 
6. _Clean up or Reference Text Files_:
After completion of the above tasks, the text files can be discarded as
  irrelevant, or used as a quick reference to the metadata info along-
  side where you're storing the files themselves.
  
### Output Types

| Flag | .ext | Description |
|:-------|:--------:|:-----------------------------------------------------------------------------|
| C | .csv   | output originally intended for use with [DSPace-Labs/SAFBuilder](https://github.com/DSpace-Labs/SAFBuilder).|
| ~~X,x~~ | ~~.xml~~ |[One or many] is a commonly used in SOAP APIs |
|~~J,j~~|~~.json~~|[One or many] is commonly used by REST APIs|
|~~M~~|~~.mrk~~|MARC format which will likely need to be compiled into .mrc|
|...|...| Feel free to fork and create more output types or suggest different uses.|

### Raw Help Output

Use `-h` at any time to get (something like) the following:

```
usage: dublin-core-text-parser

A cataloguing tool for converting specially formatted text files
containing dublin core metadata into various formats

 -c,--config <arg>   Reference to a file containing alternative header
                     arrangements
 -C,--csv            Create a single .csv  file containing metadata of
                     each item
 -h,--help           Display the help information
 -J,--json           Create a single .json file containing metadata of
                     each item
 -M,--mrk            Create a single .mrk  file containing metadata of
                     each item
 -o,--output <arg>   Name the output file
 -s,--shared <arg>   file location of the shared.csv file containing the
                     shared fields
 -X,--xml            Create a single .xml  file containing metadata of
                     each item
```

### External Links

* [Wiki for this program!](https://github.com/atla5/dublin-core-text-parser/wiki)
* [Dublin Core documentation](http://dublincore.org/documents/dces/)
* [Sample Dublin Core JSON object](https://www.w3.org/2008/WebVideo/Annotations/drafts/API10/JSON/normative_json_ma_dc.json)
* [DSpace SimpleArchiveFormat documentation](https://wiki.duraspace.org/display/DSPACE/Simple+Archive+Format+Packager)
