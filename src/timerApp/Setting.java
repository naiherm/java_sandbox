package timerApp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Setting {
	
	private List<Data> setting = null;
	
    public Setting() {
	}
	public Setting( List<Data> setting ) {
		this.setting = setting;
	}

    @XmlElementWrapper(name="list")
    @XmlElement(name="value")
	public List<Data> getSetting() {
		return this.setting;
	}

	public void setSetting(List<Data> setting) {
		this.setting = setting;
	}
}
