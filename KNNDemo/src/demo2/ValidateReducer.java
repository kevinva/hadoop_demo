package demo2;
import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class ValidateReducer extends Reducer<NullWritable, Text, DoubleWritable, NullWritable> {
	private String splitter="";
	@Override
	protected void setup(Reducer<NullWritable, Text, DoubleWritable, NullWritable>.Context context)
			throws IOException, InterruptedException {
		splitter=context.getConfiguration().get("SPLITTER");
	}
	@Override
	protected void reduce(NullWritable key, Iterable<Text> value,
			Reducer<NullWritable, Text, DoubleWritable, NullWritable>.Context context)
					throws IOException, InterruptedException {
		//��ʼ��sum��¼Ԥ�������ȷ�ĸ���
		int sum=0;
		//��ʼ��count��¼���з������ļ�¼����Ҳ���������ݵļ�¼��
		int count=0;
		for (Text val: value) {
			count++;
			String predictLabel=val.toString().split(splitter)[0];
			String trueLabel=val.toString().split(splitter)[1];
			//�ж�Ԥ����������Ƿ�����ȷ��������һ��
			if(predictLabel.equals(trueLabel)){
				sum+=1;
			}
		}
		//������ȷ��
		double accuracy=(double)sum/count;
		context.write(new DoubleWritable(accuracy), NullWritable.get());
	}
}
