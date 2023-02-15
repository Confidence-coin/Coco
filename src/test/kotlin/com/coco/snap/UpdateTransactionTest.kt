package com.coco.snap

import com.coco.api.ApiObject
import com.coco.api.UpdateTransaction
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

class UpdateTransactionTest {
    @Test
    fun testAddUpdateTransaction() {
        // Create mocks
        val nextSnap = mock(NextSnap::class.java)
        val dtp = MutableDtp().apply {
            members = mutableListOf(
                BalanceData("testAddress1", 100),
                BalanceData("testAddress2", 50)
            )
            walletAddress = "dtpAddress"
        }

        val externalDtp = MutableDtp().apply {
            members = mutableListOf(BalanceData("externalAddress", 150))
            walletAddress = "externalDtpAddress"
        }

        `when`(nextSnap.dTps).thenReturn(
            mapOf(
                "dtpAddress" to dtp,
                "externalDtpAddress" to externalDtp
            )
        )

        // Create test data
        val data = UpdateTransaction(
            snapId = 1,
            walletAddress = "dtpAddress",
            newWallets = arrayOf("newWallet"),
            memberWalletIds = arrayOf(0, 1),
            externalDtpsData = arrayOf(WalletData(arrayOf("externalAddress"), "externalDtpAddress")),
            balanceDeltas = arrayOf(10, -25, 10, 5)
        )

        // Call the function
        addUpdateTransaction(nextSnap, data)

        assertEquals(10, nextSnap.dTps["dtpAddress"]!!.members[0].balance)
        assertEquals(75, nextSnap.dTps["dtpAddress"]!!.members[1].balance)
        assertEquals(60, nextSnap.dTps["dtpAddress"]!!.members[2].balance)
        assertEquals(155, nextSnap.dTps["externalDtpAddress"]!!.members[0].balance)
    }

    @Test
    fun `rejectUpdateTransaction returns true when dtp last snap id is greater than or equal to data snap id`() {
        // Create a mock ApiObject instance with dummy data
        val mockApiObject = mock(ApiObject::class.java)
        val data = UpdateTransaction(1, "testAddress", arrayOf(), arrayOf(), arrayOf(), arrayOf())
        `when`(mockApiObject.verifySignature(data.snapId, data.walletAddress)).thenReturn(true)

        // Create a mock Dtp instance
        val mockDtp = mock(Dtp::class.java)
        // Set the last snap ID of the mock Dtp to be greater than the snap ID in the update transaction
        `when`(mockDtp.lastSnapId).thenReturn(data.snapId + 1)

        // Mock the static Snaps.dtp method
        Mockito.mockStatic(Snaps::class.java).use { mocked ->
            mocked.`when`<Dtp> {
                Snaps.dtp(data.walletAddress)
            }.thenReturn(mockDtp)

            // Call the rejectUpdateTransaction function with the mock ApiObject instance
            val result = rejectUpdateTransaction(mockApiObject)

            // Verify that the function returns true
            assertEquals(true, result)
        }
    }


}