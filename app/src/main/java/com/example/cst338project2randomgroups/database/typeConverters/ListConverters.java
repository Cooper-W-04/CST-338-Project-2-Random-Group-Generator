package com.example.cst338project2randomgroups.database.typeConverters;

import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListConverters {
    @TypeConverter
    public String fromList(List<Integer> list) {
        return list == null ? null : list.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @TypeConverter
    public List<Integer> toList(String data) {
        return data == null || data.isEmpty() ? List.of() :
                Arrays.stream(data.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
}
