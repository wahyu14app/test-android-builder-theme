package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        QianPulsaDashboard()
      }
    }
  }
}

// Data class for PPOB Service Items
data class PPOBService(val title: String, val icon: ImageVector, val color: Color, val tag: String)

// Dynamic theme configuration preset
data class WhiteLabelThemePreset(
  val name: String,
  val primary: Color,
  val secondary: Color,
  val background: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QianPulsaDashboard() {
  // State for White-Label Configuration
  var appTitle by remember { mutableStateOf("QianPulsa") }
  var sellerSubdomain by remember { mutableStateOf("wahyu.qianpulsa.id") }
  var sellerId by remember { mutableStateOf("SLR-9942") }
  var showConfigModal by remember { mutableStateOf(false) }
  var showBuildSuccessMessage by remember { mutableStateOf(false) }

  // Theme presets simulating the "test-android-builder-theme" system
  val themePresets = listOf(
    WhiteLabelThemePreset("Ocean Blue", Color(0xFF0061A4), Color(0xFF535F70), Color(0xFFF8F9FA)),
    WhiteLabelThemePreset("Emerald Green", Color(0xFF006C4C), Color(0xFF4C6356), Color(0xFFF4FBF7)),
    WhiteLabelThemePreset("Crimson Red", Color(0xFFBA1A1A), Color(0xFF775656), Color(0xFFFFF8F7)),
    WhiteLabelThemePreset("Royal Purple", Color(0xFF8600E8), Color(0xFF6F528A), Color(0xFFFAF7FF)),
    WhiteLabelThemePreset("Charcoal Black", Color(0xFF2D312E), Color(0xFF5B605C), Color(0xFFF9FAF9))
  )

  var activeThemePreset by remember { mutableStateOf(themePresets[0]) }

  // Animate colors when theme preset changes
  val animatedPrimary by animateColorAsState(targetValue = activeThemePreset.primary, label = "PrimaryColor")
  val animatedSecondary by animateColorAsState(targetValue = activeThemePreset.secondary, label = "SecondaryColor")
  val animatedBg by animateColorAsState(targetValue = activeThemePreset.background, label = "BgColor")

  // Overriding material design color scheme locally based on dynamic customization
  val customColorScheme = MaterialTheme.colorScheme.copy(
    primary = animatedPrimary,
    secondary = animatedSecondary,
    background = animatedBg,
    surface = Color.White
  )

  MaterialTheme(colorScheme = customColorScheme) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = "Wallet Logo",
                tint = animatedPrimary,
                modifier = Modifier.padding(end = 8.dp)
              )
              Text(
                text = appTitle,
                fontWeight = FontWeight.Bold,
                color = animatedPrimary,
                modifier = Modifier.testTag("app_title")
              )
            }
          },
          actions = {
            IconButton(
              onClick = { showConfigModal = true },
              modifier = Modifier.testTag("config_button")
            ) {
              Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Pengaturan SaaS",
                tint = animatedPrimary
              )
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
      },
      contentWindowInsets = WindowInsets.systemBars
    ) { innerPadding ->
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .background(animatedBg)
          .padding(innerPadding)
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        
        // --- 1. SELLER & WHITELABEL HEADER BANNER ---
        item {
          Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = animatedPrimary),
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 12.dp)
              .testTag("seller_card")
          ) {
            Column(
              modifier = Modifier.padding(20.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = "PLATFORM SAAS WHITE-LABEL",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.8f)
                  )
                  Text(
                    text = sellerSubdomain,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = "SELLER ID: $sellerId",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                }
              }

              Spacer(modifier = Modifier.height(16.dp))

              Divider(color = Color.White.copy(alpha = 0.2f))

              Spacer(modifier = Modifier.height(16.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
              ) {
                Column {
                  Text(
                    text = "Total Saldo Kas Toko",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.8f)
                  )
                  Text(
                    text = "Rp 12.750.000",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                  )
                }
                Button(
                  onClick = { /* Deposito action */ },
                  colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                  shape = RoundedCornerShape(12.dp),
                  contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Saldo",
                    tint = animatedPrimary,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "Isi Saldo",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = animatedPrimary
                  )
                }
              }
            }
          }
        }

        // --- 2. THEME BUILDER CONTROLLERS (Simulasi test-android-builder-theme) ---
        item {
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text(
                text = "Simulasi Theme Builder (Action CI/CD)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
              )
              Text(
                text = "Pilih skema warna di bawah ini untuk menguji live-preview white-label Anda.",
                fontSize = 11.sp,
                color = Color.Gray,
                lineHeight = 16.sp
              )
              
              Spacer(modifier = Modifier.height(12.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                themePresets.forEach { preset ->
                  val isSelected = activeThemePreset.name == preset.name
                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .clip(CircleShape)
                      .background(preset.primary)
                      .clickable { activeThemePreset = preset }
                      .padding(2.dp)
                  ) {
                    if (isSelected) {
                      Box(
                        modifier = Modifier
                          .fillMaxSize()
                          .clip(CircleShape)
                          .background(Color.White.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                      ) {
                        Icon(
                          imageVector = Icons.Default.Check,
                          contentDescription = "Terpilih",
                          tint = Color.White,
                          modifier = Modifier.size(16.dp)
                        )
                      }
                    }
                  }
                }
              }
              
              Spacer(modifier = Modifier.height(14.dp))

              Button(
                onClick = { showBuildSuccessMessage = true },
                colors = ButtonDefaults.buttonColors(containerColor = animatedPrimary),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("build_button"),
                shape = RoundedCornerShape(12.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.CloudUpload,
                  contentDescription = "Build"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Kirim Konfigurasi Ke GitHub Action",
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }

        // --- 3. PPOB SERVICES GRID ---
        item {
          Text(
            text = "Layanan PPOB & Kasir",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
          )
        }

        item {
          val services = listOf(
            PPOBService("Pulsa", Icons.Default.PhoneAndroid, Color(0xFFE3F2FD), "pulsa"),
            PPOBService("Paket Data", Icons.Default.SignalCellularAlt, Color(0xFFE8F5E9), "data"),
            PPOBService("Token PLN", Icons.Default.Bolt, Color(0xFFFFFDE7), "pln"),
            PPOBService("Pascabayar", Icons.AutoMirrored.Filled.ReceiptLong, Color(0xFFF3E5F5), "pasca"),
            PPOBService("Voucher Game", Icons.Default.Gamepad, Color(0xFFFFEBEE), "game"),
            PPOBService("Tagihan Air", Icons.Default.WaterDrop, Color(0xFFE0F7FA), "pdam")
          )

          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Displaying grid as 3 rows of 2 columns or manually using Rows to avoid nested scroll issues
            val chunked = services.chunked(3)
            chunked.forEach { rowItems ->
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                rowItems.forEach { svc ->
                  Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                      .weight(1f)
                      .clickable { /* Handle service click */ }
                  ) {
                    Column(
                      modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                      horizontalAlignment = Alignment.CenterHorizontally,
                      verticalArrangement = Arrangement.Center
                    ) {
                      Box(
                        modifier = Modifier
                          .size(44.dp)
                          .clip(CircleShape)
                          .background(svc.color),
                        contentAlignment = Alignment.Center
                      ) {
                        Icon(
                          imageVector = svc.icon,
                          contentDescription = svc.title,
                          tint = animatedPrimary,
                          modifier = Modifier.size(22.dp)
                        )
                      }
                      Spacer(modifier = Modifier.height(8.dp))
                      Text(
                        text = svc.title,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                      )
                    }
                  }
                }
              }
            }
          }
        }

        // --- 4. RECENT TRANSACTIONS ---
        item {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Riwayat Transaksi Terakhir",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = Color.DarkGray
            )
            Text(
              text = "Lihat Semua",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = animatedPrimary,
              modifier = Modifier.clickable { /* Navigation */ }
            )
          }
        }

        items(listOf(
          Triple("Isi Pulsa Telkomsel 50K", "081234567890", "Rp 50.200 (SUKSES)"),
          Triple("Token Listrik PLN 100K", "No. Meter: 3215546221", "Rp 101.500 (SUKSES)"),
          Triple("Paket Data Indosat 10GB", "085744312211", "Rp 34.000 (PENDING)")
        )) { trx ->
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(animatedPrimary.copy(alpha = 0.1f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = if (trx.first.contains("Pulsa")) Icons.Default.PhoneAndroid else Icons.Default.Bolt,
                    contentDescription = null,
                    tint = animatedPrimary,
                    modifier = Modifier.size(18.dp)
                  )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(text = trx.first, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                  Text(text = trx.second, fontSize = 10.sp, color = Color.Gray)
                }
              }
              Text(
                text = trx.third,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (trx.third.contains("SUKSES")) Color(0xFF2E7D32) else Color(0xFFEF6C00)
              )
            }
          }
        }

        item {
          Spacer(modifier = Modifier.height(24.dp))
        }
      }
    }

    // --- CONFIGURATION MODAL (Pengaturan SaaS) ---
    if (showConfigModal) {
      AlertDialog(
        onDismissRequest = { showConfigModal = false },
        confirmButton = {
          TextButton(onClick = { showConfigModal = false }) {
            Text("Simpan", color = animatedPrimary, fontWeight = FontWeight.Bold)
          }
        },
        dismissButton = {
          TextButton(onClick = { showConfigModal = false }) {
            Text("Batal", color = Color.Gray)
          }
        },
        title = {
          Text(
            text = "Edit Konfigurasi White-Label",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
          )
        },
        text = {
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
              value = appTitle,
              onValueChange = { appTitle = it },
              label = { Text("Nama Aplikasi") },
              modifier = Modifier.fillMaxWidth().testTag("input_app_title")
            )
            OutlinedTextField(
              value = sellerSubdomain,
              onValueChange = { sellerSubdomain = it },
              label = { Text("Subdomain Seller") },
              modifier = Modifier.fillMaxWidth().testTag("input_subdomain")
            )
            OutlinedTextField(
              value = sellerId,
              onValueChange = { sellerId = it },
              label = { Text("Seller ID") },
              modifier = Modifier.fillMaxWidth().testTag("input_seller_id")
            )
          }
        }
      )
    }

    // --- BUILD SUCCESS/SIMULATION MESSAGE ---
    if (showBuildSuccessMessage) {
      AlertDialog(
        onDismissRequest = { showBuildSuccessMessage = false },
        confirmButton = {
          Button(
            onClick = { showBuildSuccessMessage = false },
            colors = ButtonDefaults.buttonColors(containerColor = animatedPrimary)
          ) {
            Text("Selesai", color = Color.White)
          }
        },
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.CloudQueue,
              contentDescription = "Success Icon",
              tint = Color(0xFF2E7D32),
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Simulasi Build Berhasil!", fontSize = 16.sp, fontWeight = FontWeight.Bold)
          }
        },
        text = {
          Column {
            Text(
              text = "Konfigurasi White-Label Anda telah siap diekspor ke repositori GitHub untuk otomatisasi build APK menggunakan GitHub Actions.",
              fontSize = 12.sp,
              color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEEEEEE))
                .padding(10.dp)
            ) {
              Text(
                text = """
                  {
                    "appName": "$appTitle",
                    "sellerId": "$sellerId",
                    "subdomain": "$sellerSubdomain",
                    "themePreset": "${activeThemePreset.name}",
                    "primaryColor": "#${Integer.toHexString(activeThemePreset.primary.value.toInt()).substring(2).uppercase()}"
                  }
                """.trimIndent(),
                fontSize = 10.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                color = Color.DarkGray
              )
            }
          }
        }
      )
    }
  }
}
