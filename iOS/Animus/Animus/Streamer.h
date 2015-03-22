//
//  Streamer.h
//  Animus
//
//  Created by Bartlomiej Siemieniuk on 21/03/2015.
//  Copyright (c) 2015 TeamGoat. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Streamer : NSObject <NSStreamDelegate>

-(void)testStreamWithMessage:(NSString *)message;
-(void)send:(NSData *)message;
-(void)close;

@end
