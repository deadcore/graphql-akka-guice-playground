package io.igu.graphql.clients.model

import io.igu.graphql.model.{Direction, Transaction}
import spray.json.DefaultJsonProtocol

case class Fill(tradeId: Int,
                productId: String,
                price: String,
                size: String,
                orderId: String,
                createdAt: String,
                liquidity: String,
                fee: String,
                settled: Boolean,
                side: String) {

  def toTransaction: Transaction = Transaction(direction)

  private def direction: Direction.Value = if (side == "buy") Direction.BUY else Direction.SELL

}

object Fill extends DefaultJsonProtocol {
  // One of the built-in spray-json auto-formatters
  implicit val format = jsonFormat(Fill.apply, "trade_id", "product_id", "price", "size", "order_id", "created_at", "liquidity", "fee", "settled", "side")
}
