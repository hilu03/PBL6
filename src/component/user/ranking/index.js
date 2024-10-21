import React from "react";
import "./style.scss"
import { FaRankingStar } from "react-icons/fa6";
import { Formatter } from "utils/formatter";
import { useState } from "react";
import { Button } from "@mui/material";
const Ranking = () => {
    const [activeTabs, setActiveTabs] = useState(0);
    return (
        <div className="ranking">
            <div className="container">
                <div className="card rankingCard">
                    <div className="rankingHeader d-flex align-items-center">
                        <FaRankingStar/>
                        <h4>Bảng xếp hạng</h4>
                    </div>
                    <div className="rankingTabs mt-3">
                        <ul className="list list-inline d-flex">
                            <li className="list-inline-items">
                            <Button
                                className={`${activeTabs === 0 && "active"}`}
                                onClick={() => {
                                    setActiveTabs(0);
                                }}
                            >
                                Lượt xem
                            </Button>
                            </li>
                            <li className="list-inline-items">
                            <Button
                                className={`${activeTabs === 1 && "active"}`}
                                onClick={() => {
                                    setActiveTabs(1);
                                }}
                            >
                                Yêu thích 
                            </Button>
                            </li>
                        </ul>
                    </div>

                    <div className="booksInfo">
                        <div className="bookCard d-flex">
                            <div className="rankingNumber col-lg-1">
                                <h1>1</h1>
                            </div>
                            <div className="bookImage col-lg-1">
                                <img src="https://product.hstatic.net/200000343865/product/doraemon-dovuihh_bceea598fbbe49929a63d701285a6312_f24f33c0f45a472f81ddb2b47b9e9210_large.jpg" alt="" />
                            </div>
                            <div className="bookInfo col-lg-10">
                                <h4>Doraemon đố vui – Doraemon những cuộc phiêu lưu</h4>
                                <span> Lượt xem: </span>
                                <span>1234</span>
                                <br/>

                                <span>Tác giả: </span>
                                <span>Fujiko F Fujio</span>
                                <br/>

                                <span> Thể loại: </span>
                                <span>Manga-Comic</span>
                                <br/>

                                <span className="curPrice_ranking text-danger">
                                    {Formatter(1000000)}
                                </span>
                                <span className="oriPrice_ranking">
                                    {Formatter(1000000)}    
                                </span>
                            </div>
                        </div>

                        <hr/>

                        <div className="bookCard d-flex">
                            <div className="rankingNumber col-lg-1">
                                <h1>2</h1>
                            </div>
                            <div className="bookImage col-lg-1">
                                <img src="https://product.hstatic.net/200000343865/product/3_db7d9150d9784287b49f105426d1e472.jpg" alt="" />
                            </div>
                            <div className="bookInfo col-lg-10">
                                <h4>Chú thuật hồi chiến - Tập 3</h4>
                                <span> Lượt xem: </span>
                                <span>1234</span>
                                <br/>

                                <span>Tác giả: </span>
                                <span>Fujiko F Fujio</span>
                                <br/>

                                <span> Thể loại: </span>
                                <span>Manga-Comic</span>
                                <br/>

                                <span className="curPrice_ranking text-danger">
                                    {Formatter(1000000)}
                                </span>
                                <span className="oriPrice_ranking">
                                    {Formatter(1000000)}    
                                </span>
                            </div>
                        </div>
                        <hr/>

                        <div className="bookCard d-flex">
                            <div className="rankingNumber col-lg-1">
                                <h1>3</h1>
                            </div>
                            <div className="bookImage col-lg-1">
                                <img src="https://product.hstatic.net/200000343865/product/doraemon-dovuihh_bceea598fbbe49929a63d701285a6312_f24f33c0f45a472f81ddb2b47b9e9210_large.jpg" alt="" />
                            </div>
                            <div className="bookInfo col-lg-10">
                                <h4>Doraemon đố vui – Doraemon những cuộc phiêu lưu</h4>
                                <span> Lượt xem: </span>
                                <span>1234</span>
                                <br/>

                                <span>Tác giả: </span>
                                <span>Fujiko F Fujio</span>
                                <br/>

                                <span> Thể loại: </span>
                                <span>Manga-Comic</span>
                                <br/>

                                <span className="curPrice_ranking text-danger">
                                    {Formatter(1000000)}
                                </span>
                                <span className="oriPrice_ranking">
                                    {Formatter(1000000)}    
                                </span>
                            </div>
                        </div>
                        <hr/>

                        <div className="bookCard d-flex">
                            <div className="rankingNumber col-lg-1">
                                <h1>4</h1>
                            </div>
                            <div className="bookImage col-lg-1">
                                <img src="https://product.hstatic.net/200000343865/product/doraemon-dovuihh_bceea598fbbe49929a63d701285a6312_f24f33c0f45a472f81ddb2b47b9e9210_large.jpg" alt="" />
                            </div>
                            <div className="bookInfo col-lg-10">
                                <h4>Doraemon đố vui – Doraemon những cuộc phiêu lưu</h4>
                                <span> Lượt xem: </span>
                                <span>1234</span>
                                <br/>

                                <span>Tác giả: </span>
                                <span>Fujiko F Fujio</span>
                                <br/>

                                <span> Thể loại: </span>
                                <span>Manga-Comic</span>
                                <br/>

                                <span className="curPrice_ranking text-danger">
                                    {Formatter(1000000)}
                                </span>
                                <span className="oriPrice_ranking">
                                    {Formatter(1000000)}    
                                </span>
                            </div>
                        </div>
                        <hr />

                        <div className="bookCard d-flex">
                            <div className="rankingNumber col-lg-1">
                                <h1>5</h1>
                            </div>
                            <div className="bookImage col-lg-1">
                                <img src="https://product.hstatic.net/200000343865/product/doraemon-dovuihh_bceea598fbbe49929a63d701285a6312_f24f33c0f45a472f81ddb2b47b9e9210_large.jpg" alt="" />
                            </div>
                            <div className="bookInfo col-lg-10">
                                <h4>Doraemon đố vui – Doraemon những cuộc phiêu lưu</h4>
                                <span> Lượt xem: </span>
                                <span>1234</span>
                                <br/>

                                <span>Tác giả: </span>
                                <span>Fujiko F Fujio</span>
                                <br/>

                                <span> Thể loại: </span>
                                <span>Manga-Comic</span>
                                <br/>

                                <span className="curPrice_ranking text-danger">
                                    {Formatter(1000000)}
                                </span>
                                <span className="oriPrice_ranking">
                                    {Formatter(1000000)}    
                                </span>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
}

export default Ranking;