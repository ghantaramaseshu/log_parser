package com.ef.log.writer;

import java.util.List;

import com.ef.data.LogData;

public interface LogWriter {

	public int[][] write(List<LogData> logDataList);
}
