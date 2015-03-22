//
//  ViewController.m
//  Animus
//
//  Created by Bartlomiej Siemieniuk on 21/03/2015.
//  Copyright (c) 2015 TeamGoat. All rights reserved.
//

#import "ViewController.h"
#import "Streamer.h"
#import "AudioController.h"

@interface ViewController ()
@property (nonatomic,strong) AudioController * ac;
@property (nonatomic,strong) Streamer * streamer;
@property (nonatomic) BOOL isRecordng;
@property (nonatomic, strong) UIImageView * hornyButton;
@property (nonatomic, strong) UIImageView * moreHornyDwarfs;
@property (nonatomic, strong) UIImageView * hornyBus;
@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.isRecordng = NO;
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"bg-idle"]];
    
    // Horny Bus
    _hornyBus = [[UIImageView alloc] initWithFrame:
                              CGRectMake(220, 120, 100, 355)];
    [_hornyBus setImage:[UIImage imageNamed:@"horny-bus"]];
    [self.view addSubview:_hornyBus];
    
    
    UIImageView * hornyPuf = [[UIImageView alloc] initWithFrame:
                              CGRectMake(-15, 420, 100, 150)];
    [hornyPuf setImage:[UIImage imageNamed:@"horny-hup"]];
    [self.view addSubview:hornyPuf];
    
    // Horny Bus
    _moreHornyDwarfs = [[UIImageView alloc] initWithFrame:
                              CGRectMake(-30, 40, 120, 375)];
    [_moreHornyDwarfs setImage:[UIImage imageNamed:@"more-horny-dwarfs"]];
    [self.view addSubview:_moreHornyDwarfs];
    
    // Grif
    UIImageView * hornyGryf = [[UIImageView alloc] initWithFrame:
                              CGRectMake(25, -15, 147, 185)];
    [hornyGryf setImage:[UIImage imageNamed:@"horny-grif"]];
    [self.view addSubview:hornyGryf];
    
    // Grif
    UIImageView * hornyLuna = [[UIImageView alloc] initWithFrame:
                               CGRectMake(220, -15, 110, 215)];
    
    [hornyLuna setImage:[UIImage imageNamed:@"horny-luna"]];
    [self.view addSubview:hornyLuna];
    
    
    // Grif
    UIImageView * slyt = [[UIImageView alloc] initWithFrame:
                               CGRectMake(180, 50, 120, 165)];
    [slyt setImage:[UIImage imageNamed:@"horny-slut"]];
    [self.view addSubview:slyt];
    
    UIImageView * hornyRav = [[UIImageView alloc] initWithFrame:
                              CGRectMake(240, 420, 100, 150)];
    [hornyRav setImage:[UIImage imageNamed:@"horny-rav"]];
    [self.view addSubview:hornyRav];
    
    
    // Horny Dwarfy
    UIImageView * hornyDwarfs = [[UIImageView alloc] initWithFrame:
                              CGRectMake(30, 330, 280, 340)];
    [hornyDwarfs setImage:[UIImage imageNamed:@"horny-dwarfs"]];
    [self.view addSubview:hornyDwarfs];
    
    UIImageView * hornyOval = [[UIImageView alloc] initWithFrame:
                                 CGRectMake(0, 0, 320, 568)];
    [hornyOval setImage:[UIImage imageNamed:@"bg-top"]];
    [self.view addSubview:hornyOval];
    
    self.hornyButton = [[UIImageView alloc] initWithFrame:
                                 CGRectMake(100, 210, 140, 140)];
    [self.hornyButton setImage:[UIImage imageNamed:@"play-idle"]];
    [self.view addSubview:self.hornyButton];
    
    UITapGestureRecognizer *tapTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleTap:)];
    
    self.hornyButton.userInteractionEnabled = YES;
    [self.hornyButton addGestureRecognizer:tapTap];
    

    // Do any additional setup after loading the view, typically from a nib.
}

-(void)animateIn {
    [UIView animateWithDuration:0.5
                          delay:0.2
                        options: UIViewAnimationCurveEaseOut
                     animations: ^{
                         CGFloat x = self.hornyBus.frame.origin.x - 30;
                         CGFloat y = self.hornyBus.frame.origin.y;
                         CGRect frame = self.hornyBus.frame;
                         
                         [self.hornyBus setFrame:(CGRectMake(x, y, frame.size.width, frame.size.height ))];
                         
                         CGFloat x2 = self.moreHornyDwarfs.frame.origin.x + 30;
                         CGFloat y2 = self.moreHornyDwarfs.frame.origin.y;
                         CGRect frame2 = self.moreHornyDwarfs.frame;
                         
                         [self.moreHornyDwarfs setFrame:(CGRectMake(x2, y2, frame2.size.width, frame2.size.height ))];
                         
                     }
                     completion:^(BOOL finished){
                         NSLog(@"Done!");
                     }];
}

-(void)animateOut {
    [UIView animateWithDuration:0.5
                          delay:0.2
                        options: UIViewAnimationCurveEaseOut
                     animations: ^{
                         CGFloat x = self.hornyBus.frame.origin.x + 30;
                         CGFloat y = self.hornyBus.frame.origin.y;
                         CGRect frame = self.hornyBus.frame;
                         
                         [self.hornyBus setFrame:(CGRectMake(x, y, frame.size.width, frame.size.height ))];
                         
                         CGFloat x2 = self.moreHornyDwarfs.frame.origin.x - 30;
                         CGFloat y2 = self.moreHornyDwarfs.frame.origin.y;
                         CGRect frame2 = self.moreHornyDwarfs.frame;
                         
                         [self.moreHornyDwarfs setFrame:(CGRectMake(x2, y2, frame2.size.width, frame2.size.height ))];
                         
                     }
                     completion:^(BOOL finished){
                         NSLog(@"Done!");
                     }];
}

-(void)handleTap:(UITapGestureRecognizer *)tapRecognizer {
    if(self.isRecordng) {
        
        self.streamer   = [[Streamer alloc] init];
        [self.hornyButton setImage:[UIImage imageNamed:@"play-idle"]];
        [self animateIn];
//        [self.streamer testStreamWithMessage:@"stop"];

    } else {
        [self.hornyButton setImage:[UIImage imageNamed:@"play-record"]];
        [self animateOut];
        [self.streamer close];
//        [self.streamer testStreamWithMessage:@"start"];
    }
    
    self.isRecordng = !self.isRecordng;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
