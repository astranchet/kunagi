package scrum.client.sprint;


public class ClaimTaskAction extends GClaimTaskAction {

	public ClaimTaskAction(Task task) {
		super(task);
	}

	@Override
	public String getLabel() {
		return "Claim";
	}

	@Override
	public String getTooltip() {
		return "Claim ownership for this task.";
	}

	@Override
	public boolean isExecutable() {
		if (task.isClosed()) return false;
		if (task.isOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		task.claim();
	}

}
