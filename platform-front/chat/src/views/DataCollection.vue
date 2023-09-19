<template>
    <div class="form">
        <h1 class="title">Materials Data Collection</h1>
        <n-form ref="formRef" :model="model" :rules="rules" label-placement="left" label-width="auto"
            require-mark-placement="right-hanging" size="large">
            <n-form-item label="Software" path="selectValue">
                <n-select v-model:value="model.selectValue" placeholder="Software" :options="generalOptions" />
            </n-form-item>
            <n-form-item label="Description" path="inputValue">
                <n-input v-model:value="model.inputValue" placeholder="Description" />
            </n-form-item>
            <n-form-item label="Calculation Type" :show-feedback="false" path="nestedValue">
                <n-grid :cols="2" :x-gap="24">
                    <n-form-item-gi path="nestedValue.path1">
                        <n-select v-model:value="model.nestedValue.path1" placeholder="Calculation Type"
                            :options="path1Options" />
                    </n-form-item-gi>
                    <n-form-item-gi path="nestedValue.path2" v-if="model.nestedValue.path1 == 'Single Calculation Type'">
                        <n-select v-model:value="model.nestedValue.path2"
                            placeholder="Calculation Type" :options="model.selectValue == 'VASP' ? path2Options : []" />
                        <!-- <n-select v-else="model.selectValue == 'VASP'" v-model:value="model.nestedValue.path2"
                            placeholder="Calculation Type" :options="[]" /> -->
                    </n-form-item-gi>
                </n-grid>
            </n-form-item>
            <n-form-item label="Uploaded File" path="FileValue">
                <n-select v-model:value="model.FileValue" placeholder="Uploaded Select" :options="fileOptions" />
            </n-form-item>
            <n-form-item label=" " path=" ">
                <n-upload>
                    <n-button size="large">Uploaded File</n-button>
                    <!-- <n-input placeholder="Uploaded File"></n-input> -->
                </n-upload>
            </n-form-item>
            <div style="display: flex; justify-content: flex-end">
                <!-- <n-button round type="info" @click="handleValidateButtonClick">
                    Submit
                </n-button>
                <n-button round type="info" @click="handleValidateButtonClick" style="margin-left: 2rem;">
                    View results
                </n-button> -->
                <n-button round type="error" @click="handleValidateButtonClick">
                    Submit
                </n-button>
                <n-button round type="error" @click="handleValidateButtonClick" style="margin-left: 2rem;">
                    View results
                </n-button>
            </div>
        </n-form>
    </div>
</template>
  
<script setup>
const formRef = ref(null);
// const message = useMessage();
const model = ref({
    inputValue: null,
    selectValue: null,
    FileValue: null,
    nestedValue: {
        path1: null,
        path2: null
    },
})
const rules = {
    inputValue: {
        required: true,
        trigger: ["blur", "input"],
        message: "Please Enter inputValue"
    },
    selectValue: {
        required: true,
        trigger: ["blur", "change"],
        message: "Please Select selectValue"
    },
    FileValue: {
        required: true,
        trigger: ["blur", "change"],
        message: "Please Select FileValue"
    },
    nestedValue: {
        path1: {
            required: true,
            trigger: ["blur", "input"],
            message: "Please Enter Calculation Type"
        },
        path2: {
            required: true,
            trigger: ["blur", "change"],
            message: "Please Enter Calculation Type"
        }
    },
}

function handleValidateButtonClick(e) {
    e.preventDefault();
    formRef.value?.validate((errors) => {
        if (!errors) {
            // message.success("验证成功");
        } else {
            console.log(errors);
            // message.error("验证失败");
        }
    });
}
const generalOptions = ["VASP", "Abinit", "Lammps"].map(
    (v) => ({
        label: v,
        value: v
    })
)
const fileOptions = ["Single Calculation", "Multiple Calculations"].map(
    (v) => ({
        label: v,
        value: v
    })
)
const path1Options = ["Single Calculation Type", "Multiple Calculation Types"].map(
    (v) => ({
        label: v,
        value: v
    })
)
const path2Options = ["GeometryOptimization", "StaticCalculation", "ElasticProperties", "MagneticProperties", "DensityOfStates", "BandStructure", "OpticalProperties"].map(
    (v) => ({
        label: v,
        value: v
    })
)
</script>
<style lang="scss" scoped>
.form {
    width: 60%;
    margin: 0 auto;
    padding: 2rem 5rem 3rem 5rem;
    border: 1px solid #c8c5c5;

    .title {
        text-align: center;
        margin-bottom: 2rem;
    }
}
</style>