package com.example.speedy.model.breed

data class Attributes(
    val description: String,
    val female_weight: FemaleWeight,
    val hypoallergenic: Boolean,
    val life: Life,
    val male_weight: MaleWeight,
    val name: String
)