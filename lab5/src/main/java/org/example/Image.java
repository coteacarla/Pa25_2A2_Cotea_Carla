package org.example;

import java.util.List;

record Image(String name, String date, List<String> tags, String filePath) {}
