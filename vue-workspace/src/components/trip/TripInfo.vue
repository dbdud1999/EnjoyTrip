<template>
    <b-container align="left">
        <b-carousel img-width="100%" img-height="480" align="center">
            <b-carousel-slide v-if="trip.firstImage">
                <template #img>
                    <img
                    style="width:100%;height:480px;"
                    :src="trip.firstImage"
                    :alt="trip.title" 
                    >
                </template>
            </b-carousel-slide>
        </b-carousel>
        <h2 class="mt-3"> {{ trip.title }} </h2>
        <span id="addr"> 🧭 {{ trip.addr }} {{ trip.addr2 }}</span>
        <b-tooltip target="addr" placement="topright">우편번호: {{ trip.zipcode }}</b-tooltip>
        <b-card class="mt-3">
            <b-card-text>{{ trip.overview }}</b-card-text>
        </b-card>
        <div class="mt-3" align="center">
            <b-button to="/trip/list">목록으로</b-button>
        </div>
    </b-container>
</template>

<script>
export default {
    data() {
        return {
            trip: {
                contentId: this.$route.params.contentId,
            }
        };
    },
    created() {        
        // 관광지 정보 가져오기
        this.$axios({
            url: "trip/view",
            method: "POST",
            data: { contentId: this.trip.contentId }
        }).then((response) => {
            this.trip = response.data;
            console.log(this.trip);
        });
    }
}
</script>

<style scoped>

</style>