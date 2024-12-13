package com.example.assessmenttask

import ListAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.assessmenttask.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

private lateinit var binding: ActivityMainBinding

private val images = listOf(
    R.drawable.apple,
    R.drawable.orange,
    R.drawable.blueberry,
    R.drawable.banana
)

private val dataLists = listOf(
    listOf(
        ItemData(R.drawable.apple, "Apple", "Fruit"),
        ItemData(R.drawable.apple, "Banana", "Fruit"),
        ItemData(R.drawable.apple, "Orange", "Fruit"),
        ItemData(R.drawable.apple, "Blueberry", "Berry"),
        ItemData(R.drawable.apple, "Apple", "Fruit"),
        ItemData(R.drawable.apple, "Banana", "Fruit"),
        ItemData(R.drawable.apple, "Orange", "Fruit"),
        ItemData(R.drawable.apple, "Blueberry", "Berry"),
        ItemData(R.drawable.apple, "Apple", "Fruit"),
        ItemData(R.drawable.apple, "Banana", "Fruit"),
        ItemData(R.drawable.apple, "Orange", "Fruit"),
        ItemData(R.drawable.apple, "Blueberry", "Berry")
    ),
    listOf(
        ItemData(R.drawable.orange, "Cat", "Animal"),
        ItemData(R.drawable.orange, "Dog", "Animal"),
        ItemData(R.drawable.orange, "Elephant", "Animal"),
        ItemData(R.drawable.orange, "Fox", "Animal")
    ),
    listOf(
        ItemData(R.drawable.blueberry, "Guitar", "Instrument"),
        ItemData(R.drawable.blueberry, "Piano", "Instrument"),
        ItemData(R.drawable.blueberry, "Violin", "Instrument"),
        ItemData(R.drawable.blueberry, "Drums", "Instrument")
    ),
    listOf(
        ItemData(R.drawable.blueberry, "nature", "something"),
        ItemData(R.drawable.blueberry, "any", "something"),
        ItemData(R.drawable.blueberry, "ok", "something"),
        ItemData(R.drawable.blueberry, "Buy", "something")
    ),
)


private val filteredData = mutableListOf<ItemData>()
private var currentPageIndex = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filteredData.addAll(dataLists[0])
        val adapter = ListAdapter(filteredData)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.viewPagerCarousel.adapter = ImageCarouselAdapter(images)
        TabLayoutMediator(binding.tabLayoutIndicator, binding.viewPagerCarousel) { _, _ -> }.attach()

        binding.viewPagerCarousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPageIndex = position
                updateList(adapter)
            }
        })

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().lowercase()
                val filteredList = dataLists[currentPageIndex].filter {
                    it.name.lowercase().contains(query)
                }
                filteredData.clear()
                filteredData.addAll(filteredList)
                adapter.notifyDataSetChanged()
            }
        })

        binding.fab.setOnClickListener { showBottomSheetDialog() }
    }

    private fun updateList(adapter: ListAdapter) {
        filteredData.clear()
        filteredData.addAll(dataLists[currentPageIndex])
        adapter.notifyDataSetChanged()
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, null)
        dialog.setContentView(view)

        val itemCount = filteredData.size
        val charCount = filteredData
            .joinToString("") {  (it.name + it.subName).lowercase() }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { (_, value) -> value }
            .take(3)

        view.findViewById<TextView>(R.id.itemCount).text = "Items: $itemCount"
        view.findViewById<TextView>(R.id.topChars).text = charCount.joinToString("\n") {
            "${it.first} = ${it.second}"
        }

        dialog.show()
    }

}
data class ItemData(val image: Int, val name: String, val subName: String)