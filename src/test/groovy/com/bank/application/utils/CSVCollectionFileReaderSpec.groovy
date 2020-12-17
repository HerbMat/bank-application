package com.bank.application.utils

import spock.lang.Specification

class CSVCollectionFileReaderSpec extends Specification {
    def "It converts csv file to list of map entries successfully"() {
        when:
        def records = CSVCollectionFileReader.INSTANCE.extractCollectionDataFromFile("dummyCsv.csv")
        then:
        records.size() == 3
        records[0].get("name") == "name1"
        records[0].get("value") == "value1"
        records[0].get("description") == "desc1"
        records[1].get("name") == "name2"
        records[1].get("value") == "value2"
        records[1].get("description") == "desc2"
        records[2].get("name") == "name3"
        records[2].get("value") == "value3"
        records[2].get("description") == "desc3"
    }
}
