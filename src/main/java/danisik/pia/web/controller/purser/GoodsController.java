package danisik.pia.web.controller.purser;

import danisik.pia.Constants;
import danisik.pia.service.purser.GoodsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GoodsController {

	private GoodsManager goodsManager;

	public GoodsController(GoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	@GetMapping("wares/info")
	public ModelAndView waresInfo() {
		ModelAndView modelAndView = new ModelAndView("purser/goods/infoListWares");

		ModelMap modelMap = modelAndView.getModelMap();

		modelMap.addAttribute(Constants.ATTRIBUTE_NAME_WARES, goodsManager.getGoods());

		return modelAndView;
	}
}
