package scrum.mda;

import ilarkesto.di.app.ApplicationStarter;
import ilarkesto.mda.AGeneratorApplication;
import ilarkesto.mda.gen.GwtApplicationGenerator;
import ilarkesto.mda.gen.GwtDaoGenerator;
import ilarkesto.mda.gen.GwtEntityGenerator;
import ilarkesto.mda.gen.GwtServiceAsyncInterfaceGenerator;
import ilarkesto.mda.gen.GwtServiceImplementationGenerator;
import ilarkesto.mda.gen.GwtServiceInterfaceGenerator;
import ilarkesto.mda.model.ApplicationModel;
import ilarkesto.mda.model.BeanModel;
import ilarkesto.mda.model.EntityModel;
import ilarkesto.mda.model.GwtServiceModel;
import ilarkesto.mda.model.PackageModel;

import java.util.Map;

public class ScrumModelApplication extends AGeneratorApplication {

	// ----------------
	// --- entities ---
	// ----------------

	private EntityModel projectModel;

	public EntityModel getProjectModel() {
		if (projectModel == null) {
			projectModel = createEntityModel("Project", "project");
			autowire(projectModel);
			projectModel.addProperty("label", String.class).setMandatory(true).setSearchable(true);
			projectModel.addProperty("description", String.class);
			projectModel.addSetReference("admins", getUserModel());
			projectModel.addReference("productOwner", getUserModel());
			projectModel.addReference("scrumMaster", getUserModel());
			projectModel.addSetReference("teamMembers", getUserModel());
			projectModel.addReference("currentSprint", getSprintModel());
		}
		return projectModel;
	}

	private EntityModel storyModel;

	public EntityModel getStoryModel() {
		if (storyModel == null) {
			storyModel = createEntityModel("Story", "project");
			autowire(storyModel);
			storyModel.addReference("project", getProjectModel()).setMaster(true);
			storyModel.addReference("sprint", getSprintModel());
			storyModel.addProperty("label", String.class);
			storyModel.addProperty("description", String.class);
			storyModel.addProperty("testDescription", String.class);
			storyModel.addProperty("effort", Integer.class);
			storyModel.addProperty("closed", boolean.class);
		}
		return storyModel;
	}

	private EntityModel sprintModel;

	public EntityModel getSprintModel() {
		if (sprintModel == null) {
			sprintModel = createEntityModel("Sprint", "sprint");
			autowire(sprintModel);
			sprintModel.addReference("project", getProjectModel()).setMaster(true);
			sprintModel.addProperty("label", String.class);
		}
		return sprintModel;
	}

	private EntityModel taskModel;

	public EntityModel getTaskModel() {
		if (taskModel == null) {
			taskModel = createEntityModel("Task", "sprint");
			autowire(taskModel);
			taskModel.addReference("story", getStoryModel()).setMaster(true);
			taskModel.addProperty("label", String.class);
			taskModel.addProperty("effort", Integer.class);
			taskModel.addProperty("notice", String.class);
		}
		return taskModel;
	}

	private EntityModel impedimentModel;

	public EntityModel getImpedimentModel() {
		if (impedimentModel == null) {
			impedimentModel = createEntityModel("Impediment", "impediments");
			autowire(impedimentModel);
			impedimentModel.addReference("project", getProjectModel()).setMaster(true);
			impedimentModel.addProperty("label", String.class);
			impedimentModel.addProperty("description", String.class);
			impedimentModel.addProperty("solution", String.class);
			impedimentModel.addProperty("solved", boolean.class);
		}
		return impedimentModel;
	}

	private EntityModel userModel;

	@Override
	public EntityModel getUserModel() {
		if (userModel == null) {
			userModel = createEntityModel("User", "admin");
			autowire(userModel);
			userModel.setSuperbean(super.getUserModel());
			userModel.addProperty("name", String.class);
		}
		return userModel;
	}

	// ---------------
	// --- service ---
	// ---------------

	private GwtServiceModel serviceModel;

	public GwtServiceModel getServiceModel() {
		if (serviceModel == null) {
			serviceModel = createGwtServiceModel("scrum");
			autowire(serviceModel);
			serviceModel.addMethod("ping");
			serviceModel.addMethod("login").addParameter("username", String.class).addParameter("password",
				String.class);
			serviceModel.addMethod("selectProject").addParameter("projectId", String.class);
			serviceModel.addMethod("requestImpediments");
			serviceModel.addMethod("requestStorys");
			serviceModel.addMethod("requestCurrentSprint");
			serviceModel.addMethod("changeProperties").addParameter("entityId", String.class).addParameter(
				"properties", Map.class);
			serviceModel.addMethod("createEntity").addParameter("type", String.class).addParameter("properties",
				Map.class);
			serviceModel.addMethod("deleteEntity").addParameter("entityId", String.class);
			serviceModel.addMethod("sleep").addParameter("millis", long.class);
		}
		return serviceModel;
	}

	// -------------------
	// --- application ---
	// -------------------

	private ApplicationModel applicationModel;

	public ApplicationModel getApplicationModel() {
		if (applicationModel == null) {
			applicationModel = createWebApplicationModel("Scrum");
			autowire(applicationModel);
			applicationModel.addDaosAsComposites(getFinalEntityModels(true));
			applicationModel.addService(getServiceModel());
		}
		return applicationModel;
	}

	@Override
	protected String getBasePackageName() {
		return "scrum";
	}

	private PackageModel basePackageModel;

	@Override
	public PackageModel getBasePackageModel() {
		if (basePackageModel == null) {
			basePackageModel = new PackageModel(getBasePackageName()).getSubPackageModel("server");
			autowire(basePackageModel);
		}
		return basePackageModel;
	}

	private GwtServiceModel createGwtServiceModel(String name) {
		return new GwtServiceModel(name, getBasePackageModel());
	}

	public static void main(String[] args) throws InterruptedException {
		ApplicationStarter.startApplication(ScrumModelApplication.class).generateClasses();
		Thread.sleep(1000);
		System.exit(0);
	}

	@Override
	protected void generate(BeanModel beanModel) {
		super.generate(beanModel);
		if (beanModel instanceof EntityModel) {
			autowire(new GwtEntityGenerator()).generate(beanModel);
		}
	}

	@Override
	public void generateClasses() {
		super.generateClasses();
		autowire(new GwtServiceInterfaceGenerator()).generate(getServiceModel());
		autowire(new GwtServiceAsyncInterfaceGenerator()).generate(getServiceModel());
		autowire(new GwtServiceImplementationGenerator()).generate(getServiceModel());
		autowire(new GwtApplicationGenerator()).generate(getApplicationModel());
		autowire(new GwtDaoGenerator()).generate(getApplicationModel(), getFinalEntityModels(false));
		// autowire(new GwtDataTransferObjectGenerator()).generate(getApplicationModel(), getEntityModels());
	}
}