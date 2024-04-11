package org.cloudbus.cloudsim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.core.CloudSim;

/**
 * VmAllocationPolicyBestFit is a VmAllocationPolicy that chooses, as the host for a VM, the host
 * with the least available PEs but enough to accommodate the VM.
 */
public class VmAllocationPolicyBestFit extends VmAllocationPolicy {

    /** The vm table. */
    private Map<String, Host> vmTable;

    /** The used pes. */
    private Map<String, Integer> usedPes;

    /** The free pes. */
    private List<Integer> freePes;

    /**
     * Creates a new VmAllocationPolicyBestFit object.
     * 
     * @param hostList the list of hosts
     * @pre $none
     * @post $none
     */
    public VmAllocationPolicyBestFit(List<? extends Host> hostList) {
        super(hostList);

        setFreePes(new ArrayList<Integer>());
        for (Host host : getHostList()) {
            getFreePes().add(host.getNumberOfPes());
        }

        setVmTable(new HashMap<String, Host>());
        setUsedPes(new HashMap<String, Integer>());
    }

    /**
     * Allocates a host for a given VM.
     * 
     * @param vm the VM to allocate
     * @return true if the host could be allocated; false otherwise
     * @pre $none
     * @post $none
     */
    @Override
    public boolean allocateHostForVm(Vm vm) {
        int requiredPes = vm.getNumberOfPes();
        int tries = 0;

        while (tries < getFreePes().size()) {
            int minFreePes = Integer.MAX_VALUE;
            int selectedHostIdx = -1;

            // Find the host with the least available PEs but enough to accommodate the VM
            for (int i = 0; i < getFreePes().size(); i++) {
                int freePes = getFreePes().get(i);
                if (freePes >= requiredPes && freePes < minFreePes) {
                    minFreePes = freePes;
                    selectedHostIdx = i;
                }
            }

            if (selectedHostIdx != -1) {
                Host host = getHostList().get(selectedHostIdx);
                if (host.vmCreate(vm)) { // If the VM is successfully created on the host
                    getVmTable().put(vm.getUid(), host);
                    getUsedPes().put(vm.getUid(), requiredPes);
                    getFreePes().set(selectedHostIdx, minFreePes - requiredPes);
                    return true;
                }
            }
            tries++;
        }
        return false;
    }

    /**
     * Releases the host used by a VM.
     * 
     * @param vm the VM to deallocate
     * @pre $none
     * @post none
     */
    @Override
    public void deallocateHostForVm(Vm vm) {
        Host host = getVmTable().remove(vm.getUid());
        int idx = getHostList().indexOf(host);
        int pes = getUsedPes().remove(vm.getUid());
        if (host != null) {
            host.vmDestroy(vm);
            getFreePes().set(idx, getFreePes().get(idx) + pes);
        }
    }

    /**
     * Gets the host that is executing the given VM.
     * 
     * @param vm the VM
     * @return the host executing the VM; null if not found
     * @pre $none
     * @post $none
     */
    @Override
    public Host getHost(Vm vm) {
        return getVmTable().get(vm.getUid());
    }

    /**
     * Gets the VM's host specified by its ID and user ID.
     * 
     * @param vmId the VM ID
     * @param userId the user ID
     * @return the host executing the VM; null if not found
     * @pre $none
     * @post $none
     */
    @Override
    public Host getHost(int vmId, int userId) {
        return getVmTable().get(Vm.getUid(userId, vmId));
    }

    /**
     * Gets the VM table.
     * 
     * @return the VM table
     */
    public Map<String, Host> getVmTable() {
        return vmTable;
    }

    /**
     * Sets the VM table.
     * 
     * @param vmTable the VM table
     */
    protected void setVmTable(Map<String, Host> vmTable) {
        this.vmTable = vmTable;
    }

    /**
     * Gets the used PEs.
     * 
     * @return the used PEs
     */
    protected Map<String, Integer> getUsedPes() {
        return usedPes;
    }

    /**
     * Sets the used PEs.
     * 
     * @param usedPes the used PEs
     */
    protected void setUsedPes(Map<String, Integer> usedPes) {
        this.usedPes = usedPes;
    }

    /**
     * Gets the free PEs.
     * 
     * @return the free PEs
     */
    protected List<Integer> getFreePes() {
        return freePes;
    }

    /**
     * Sets the free PEs.
     * 
     * @param freePes the new free PEs
     */
    protected void setFreePes(List<Integer> freePes) {
        this.freePes = freePes;
    }

    @Override
    public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
        // Not implemented for this policy
        return null;
    }

    @Override
    public boolean allocateHostForVm(Vm vm, Host host) {
        // Not implemented for this policy
        return false;
    }
}