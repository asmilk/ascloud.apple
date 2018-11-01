package ascloud.apple.netflix.modl;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FooModel implements Serializable {
	
	private static final long serialVersionUID = -727053798007615698L;

	private Long id;

	private String name;

	public FooModel() {
		super();
	}

	public FooModel(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "FooModel [id=" + id + ", name=" + name + "]";
	}

}
