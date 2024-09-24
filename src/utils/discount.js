export const Discount = (originalPrice, discountedPrice) => {
    discountedPrice = ((originalPrice - discountedPrice) / originalPrice) * 100
    return Math.round(discountedPrice)
}