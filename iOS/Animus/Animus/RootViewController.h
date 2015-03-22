//
//  RootViewController.h
//  Animus
//
//  Created by Bartlomiej Siemieniuk on 21/03/2015.
//  Copyright (c) 2015 TeamGoat. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RootViewController : UIViewController <UIPageViewControllerDelegate>

@property (strong, nonatomic) UIPageViewController *pageViewController;

@end

